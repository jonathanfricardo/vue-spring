// see https://www.npmjs.com/package/fetch-intercept
import fetchIntercept from 'fetch-intercept';

export class FetchInterceptor {
    static theInstance; // the singleton instance that has been registered
    session;     // the sessionService which tracks the authorisation
    router;
    unregister;         // callback function to unregister this instance

    constructor(session, router) {
        this.session = session;
        this.router = router;
        FetchInterceptor.theInstance = this;
        // fetchIntercept does not register the object closure, only the methods as functions
        this.unregister = fetchIntercept.register(this);
        console.log("FetchInterceptor has been registered; current token = ", FetchInterceptor.theInstance.session.currentToken);
    }

    request(url, options) {
        let token = FetchInterceptor.theInstance.session.currentToken;
        //console.log("FetchInterceptor request: ", url, options, token);

        // if (token == null) {
        //     return [url, options];
        // } else if (options == null) {
        //     return [url, { headers: { Authorization: token }}]
        // } else {
        //     let newOptions = { ...options, headers: { Authorization: token }};
        //     // TODO combine existing headers with new Authorization header
        //
        //
        //     // console.log("FetchInterceptor request: ", url, newOptions);
        //     return [url, newOptions];
        // }

        if (token == null) {
            return [url, options];
        } else if (options == null) {
            return [url, { headers: { Authorization: token } }];
        } else {
            const newOptions = { ...options };
            // Combine existing headers with the new Authorization header
            newOptions.headers = { ...newOptions.headers, Authorization: token };
            return [url, newOptions];
        }
    }

    requestError(error) {
        // Called when an error occured during another 'request' interceptor call
        console.log("FetchInterceptor requestError: ", error);
        return Promise.reject(error);
    }

    response(response) {
        // console.log("FetchInterceptor response: ", response);
        FetchInterceptor.theInstance.tryRecoverNewJWToken(response);
        if (response.status >= 400 && response.status < 600) {
            FetchInterceptor.theInstance.handleErrorInResponse(response);
        }
        return response;
    }

    responseError(error) {
        // Handle a fetch error
        console.log("FetchInterceptor responseError: ", error);
        return Promise.reject(error);
    }

    async handleErrorInResponse(response) {
        if (response.status === 401) {
            // TODO handle an UNAUTHORISED response
            // unauthorised request, redirect to signIn page
            // this.router.navigate(['/sign-out']);    // ng-router
            this.router.push({ path: '/sign-out',});   // vue-router
        } else if (response.status !== 406) {
            // 406='Not Acceptable' error is used for logon failure
            // TODO handle any other error
        }
    }

    // eslint-disable-next-line no-unused-vars
    tryRecoverNewJWToken(response) {
        // TODO check the response on availability of a JWT
        //  and request the session service to save that
    }
}
