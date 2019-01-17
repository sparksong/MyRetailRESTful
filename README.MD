RESTful service used to retrieve product information from external API (redsky.target.com) and price information from a NoSQL data store (MongoDB), combining the information and returning product information.
-----------

Instructions to run the application locally:
-----------

1. First the application will need to be pulled in to your local workspace.

2. Build the application by running the gradle commands.

3. Run MongoDB locally and create a database.
   I used a test database and ProductPriceData as the collection which I can GET from and PUT to.
   Example used to insert in the database for testing using products with information from the API:

   db.ProductPriceData.insertMany([
      { id: "50608360", value: "13.49", currencyCode: "USD"},
      { id: "13860428", value: "20.20", currencyCode: "USD"},
      { id: "13860429", value: "1.99", currencyCode: "USD"},
      { id: "53549317", value: "18.88", currencyCode: "USD"},
      { id: "17088992", value: "137.01", currencyCode: "CAN"},
   ]);

4. After project is built locally and database is created with MongoDB:
   Run "gradlew bootRun" in the command line / terminal.
   Application will run at localhost:8080. 

5. To perform a GET to retrieve Product data from the redsky API:

Ex: "localhost:8080/product/13860428" into the search bar of your browser and you should receive a response (currentPrice may vary):
Ex. {"productData":{"id":"13860429","name":"SpongeBob SquarePants: SpongeBob's Frozen Face-off"},"currentPrice":{"value":"1.99","currencyCode":"USD"}}

If a call is made using an ID not found in redsky API your response will not contain a product name

Ex: {"productData":{"id":"13869","name":""},"currentPrice":{"value":"","currencyCode":""}}

6. To perform a PUT to add / update Price information your endpoint will add the value and currencyCode fields.

Ex: "localhost:8080/product/13860428/value=10.99/currencyCode=USD"

If value or currencyCode are entered blank, the price in the database will not update. If only one value changes, the database will still update.

PUT Ex. localhost:8080/product/17088992/value=199.99/currencyCode=

Ex. (Before) {"productData":{"id":"17088992","name":"Elmer's&#174; Washable Glue Sticks Disappearing Purple - 6ct"},"currentPrice":{"value":"17.09","currencyCode":"USD"}}
Ex. (After) {"productData":{"id":"17088992","name":"Elmer's&#174; Washable Glue Sticks Disappearing Purple - 6ct"},"currentPrice":{"value":"199.99","currencyCode":"USD"}}

