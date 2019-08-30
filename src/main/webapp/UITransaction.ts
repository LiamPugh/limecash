export class UITransaction {

    constructor(
        public name: string,
        public date: string,
        public value: string,
        public quantity: string,
        public incoming: string,
        public complete: string,
        public accountImpacted: string,
    ) {  }

}