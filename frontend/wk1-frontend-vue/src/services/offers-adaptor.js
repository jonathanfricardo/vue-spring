import {Offer} from "@/models/offer";

export class OffersAdaptor {

    resourcesUrl;
    constructor(resourcesUrl) {
        this.resourcesUrl = resourcesUrl;
        console.log("Created OffersAdaptor for " + resourcesUrl);
    }

    async fetchJson(url, options = null) {

        let response = await fetch(url, options);
        if(response.ok) {
            return await  response.json();
        } else {
            console.log(response, !response.bodyUsed ? await response.text() : "");
            return null;
        }

    }

    async asyncFindAll() {
        const offers = await this.fetchJson(this.resourcesUrl);
        return offers?.map(offer => Offer.copyConstructor(offer))
    }

    async asyncFindById(id) {
        const offer = await this.fetchJson(this.resourcesUrl + "/" + id);
        return Offer.copyConstructor(offer);
    }

    async asyncSave(offer) {
        if(offer.id === 0) {
            //offer id is 0, so we create a new offer
            return this.fetchJson(this.resourcesUrl, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        id: offer.id,
                        title: offer.title,
                        description: offer.description,
                        status: offer.status,
                        sellDate: offer.sellDate,
                        valueHighestBid: offer.valueHighestBid
                    })
                }
            )
        } else {
            //offer id isn't 0, so we update the given offer.
            return this.fetchJson(this.resourcesUrl + "/" + offer.id, {
                    method: 'PUT',
                    headers: {
                        'ContentType': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        id: offer.id,
                        title: offer.title,
                        description: offer.description,
                        status: offer.status,
                        sellDate: offer.sellDate,
                        valueHighestBid: offer.valueHighestBid
                    })
                }
            )
        }
    }

    async asyncDelete(id) {
        return this.fetchJson(this.resourcesUrl + "/" + id, {
            method: 'DELETE'
        })
    }




}