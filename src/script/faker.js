import {Worker} from 'worker_threads';
import pg from 'pg';
import {faker} from '@faker-js/faker';
import {uniqueProductNames} from "./workers/CheckUnique.js";

const client = new pg.Client({
    host: 'localhost',
    database: 'northwind',
    port: 5432,
})
await client.connect()

function getUniqueContactTitles() {
    return ['Sales Manager',
        'Owner',
        'Owner/Marketing Assistant',
        'Accounting Manager',
        'Sales Associate',
        'Sales Agent',
        'Assistant Sales Agent',
        'Assistant Sales Representative',
        'Order Administrator',
        'Marketing Manager',
        'Sales Representative',
        'Marketing Assistant'
    ];
}

function checkUniqueAsync(tableName) {
    for (let i = 0; i < 5; i++) {
        new Worker("./workers/CheckUnique.js", {workerData: tableName});
    }
}

async function initProducts() {
    const p = [];
    await client.query('TRUNCATE products RESTART IDENTITY CASCADE');
    for (const item of uniqueProductNames()) {
        const productName = item;
        const supplierId = faker.datatype.number({min: 1, max: 29}); // suppliers have 29 items
        const categoryId = faker.datatype.number({min: 1, max: 8}); // categories have 8 items
        const quantityPerUnit = '';
        const unitPrice = faker.datatype.number({min: 0, max: 1000, precision: 0.01});
        const unitInStock = faker.datatype.number({min: 0, max: 3000});
        const unitInOrder = faker.datatype.number({min: 0, max: 1000});
        const reOrderLevel = faker.datatype.number({min: 0, max: 2000});
        const discontinued = faker.datatype.number({min: 0, max: 1});

        const text = 'INSERT INTO products(product_name, supplier_id, category_id, quantity_per_unit, unit_price, units_in_stock, units_on_order, reorder_level, discontinued) VALUES($1, $2, $3, $4, $5, $6, $7, $8, $9)';
        const values = [productName, supplierId, categoryId, quantityPerUnit, unitPrice, unitInStock, unitInOrder, reOrderLevel, discontinued];
        p.push(client.query(text, values))
    }
    return Promise.all(p);
}

try {
    console.time('time');
    // await initProducts();
    checkUniqueAsync('customers_company_name');
    console.timeEnd('time');
} catch (e) {
    console.error(e);
} finally {
    client.end();
}
