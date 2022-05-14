import {parentPort, threadId, workerData} from 'worker_threads';
import {faker} from "@faker-js/faker";

export function uniqueProductNames() {
    const s = new Set();
    for (let i = 0; i < 100000; i++) {
        const productName = faker.commerce.productName();
        s.add(productName);
    }
    return s;
}

export function uniqueContactNames() {
    const s = new Set();
    for (let i = 0; i < 28000000; i++) {
        const contactName = faker.name.firstName() + faker.name.lastName();
        s.add(contactName);
    }
    return s;
}

export function uniqueCompanyName() {
    const s = new Set();
    for (let i = 0; i < 10000000; i++) {
        const companyName = faker.company.companyName();
        s.add(companyName);
    }
    return s;
}

switch (workerData) {
    case 'customers_contact_name': console.log(`Thread ${threadId}:  ${uniqueContactNames().size}`); break;
    case 'customers_company_name': console.log(`Thread ${threadId}:  ${uniqueCompanyName().size}`); break;
    case 'products_product_name': console.log(`Thread ${threadId}:  ${uniqueProductNames().size}`); break;
}
