import {fetch} from "whatwg-fetch";

export class SessionSbService {
    RESOURCES_URL;              // the back-end base url for authentication resources
    BROWSER_STORAGE_ITEM_NAME;  // the key into browser storage for retaining the token and account
    _currentToken;              // the current authentication token of this session
                                // to be injected in the authorization header of every outgoing request
    _currentUser;            // the account instant of the currently logged on user


    constructor(resourcesUrl, browserStorageItemName) {
        console.log("Created SessionService...");
        this.BROWSER_STORAGE_ITEM_NAME = browserStorageItemName;
        this.RESOURCES_URL = resourcesUrl;
        this._currentUser = null;
        this._currentToken = null;
        // retrieve the current user info from browser storage,
        // e.g. after a page reload or when a new tab is opened.
        this.getTokenFromBrowserStorage();
        this._resourcesUrl = resourcesUrl;
        this._browserStorageItemName = browserStorageItemName;
    }

    async asyncSignIn(email, password) {
        const body = JSON.stringify({ email: email, password: password });
        let response = await fetch(this.RESOURCES_URL + "/login",
            {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: body,
                credentials: 'include'
            })
        if (response.ok) {
            console.log(response)
            let account = await response.json();
            this.saveTokenIntoBrowserStorage(
                response.headers.get('Authorization'),
                account);
            return account;
        } else {
            console.log(response)
            console.log("het is niet gelukt" + response)
            return null;
        }
    }

    signOut() {
        this.saveTokenIntoBrowserStorage(null, null);
    }

    saveTokenIntoBrowserStorage(token, account) {
        console.log("methode saveTokenIntoBrowserStorage() word aangeroepen")
        console.log("Dit is het token: " + token)
        this._currentToken = token;
        this._currentUser = account;
        // allow for different user sessions from the same computer
        // sessionStorage keeps different items per browser tab
        // localStorage keeps a single item per browser vendor
        // both isolate the items per server domain of the page
        if (token == null) {
            this._currentUser = null;
            window.sessionStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME);
            window.sessionStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME+"_ACC");
            // TODO remove the token+account from local storage, if localStorage and session storage are equal
            // if (window.localStorage === window.sessionStorage){
            //     window.localStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME)
            //     window.localStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME+"_ACC")
            // }
            console.log("token is null")
        } else {
            console.log("New token for " + account.name + ": " + token);
            window.sessionStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, token);
            window.sessionStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME+"_ACC", JSON.stringify(account));
            // TODO also save the new token+account in localStorage
            // window.localStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, token);
            // window.localStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME+"_ACC", JSON.stringify(account));
            console.log("accounts wordt gesaved")
        }
    }

    getTokenFromBrowserStorage() {

        if (this._currentToken != null) return this._currentToken
        this._currentToken = window.sessionStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME);
        let jsonAccount = window.sessionStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME+"_ACC");

        if (this._currentToken == null) {
            // TODO try to find the token+account in local storage and replicate to this session if found
            this._currentToken = window.localStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME);
            jsonAccount = window.localStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME+"_ACC");
            window.sessionStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, this._currentToken)
            window.sessionStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME+"_ACC", jsonAccount)
        }
        if (jsonAccount != null) {
            this._currentUser = JSON.parse(jsonAccount);
        }
        console.log("SessionService recovered token: ", this._currentToken);
        console.log("Current Account:", this._currentUser);
        return this._currentToken;
    }

    isAuthenticated() {
        return this._currentUser != null;
    }


    get resourcesUrl() {
        return this._resourcesUrl;
    }

    set resourcesUrl(value) {
        this._resourcesUrl = value;
    }

    get browserStorageItemName() {
        return this._browserStorageItemName;
    }

    set browserStorageItemName(value) {
        this._browserStorageItemName = value;
    }

    get currentUser() {
        return this._currentUser;
    }

    set currentUser(value) {
        this._currentUser = value;
    }

    get currentToken() {
        return this._currentToken;
    }

    set currentToken(value) {
        this._currentToken = value;
    }
}