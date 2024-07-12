class Offer {

    static statuses = {
        NEW: "NEW",
        FOR_SALE: "FOR_SALE",
        SOLD: "SOLD",
        PAID: "PAID",
        DELIVERED: "DELIVERED",
        CLOSED: "CLOSED",
        EXPIRED: "EXPIRED",
        WITHDRAWN: "WITHDRAWN"
    }
    constructor(id, title, description, status, sellDate, valueHighestBid) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.sellDate = sellDate;
        this.valueHighestBid = valueHighestBid
    }

    // creates a sample offer using math.random to generate random values
    static createSampleOffer(pId = 0) {

        //assigns a random status
        const status = Object.values(this.statuses)
        const randomStatus = status[Math.floor(Math.random() * status.length)]

        //assigns a random date
        const currentDate = new Date();
        const randomDays = Math.floor(Math.random() * 365);
        const randomSellDate = new Date(currentDate);
        randomSellDate.setDate(currentDate.getDate() + randomDays)

        //random bid
        const randomValueHighestBid = randomStatus === "NEW" ? null : Math.round((Math.random() * 1000) * 100)/100;

        return new Offer(
                pId,
                "Title for Offer " + pId,
                "Description for Offer " + pId,
                randomStatus,
                randomSellDate,
                randomValueHighestBid
            );

    }

    static copyConstructor(offer) {
        //checks if given offer is null
        if (offer == null) return null;

        //if offer isnt null, create a copy
        let copy = Object.assign(new Offer(), offer);
        copy.sellDate = new Date(offer.sellDate)
        return copy
    }

}

export {Offer}

