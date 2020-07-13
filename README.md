# Viventor banking application
Technical task for Viventor

* Created Client API for signup and banking related actions

Method  | Path | Explanation
------------- | ------------- | ------------- |
POST  | /clients/signup | Register a new client |
POST  | /clients/deposit | Deposit a certain amount of money |
POST  | /clients/withdraw | Withdraw a certain amount of money |
GET  | /clients/balance | Get client balance |
GET  | /clients/statements | Get client statements |

* Endpoints except signup require basic authentication to be accessed

# Required dependencies to run the project

* Maven (download from https://maven.apache.org/download.cgi)

* JDK 8 (download from https://adoptopenjdk.net/?variant=openjdk8)

## Steps to run

* Navigate to the root of the project

* Run "mvn clean install"

* Run "java -jar target/banking-app-0.0.1-SNAPSHOT.jar"

* API will become available on http://localhost:8080/

* H2 database panel is available at http://localhost:8080/h2-console/

   * jdbc url - jdbc:h2:mem:viventor
   * username - viventor
   * password - banking
   
## Initial data

- A user with email "banker@gmail.com" and password "banker" are created on application startup

## Example CURL requests

- Get statements `curl --location --request GET "localhost:8080/clients/statements" --header "Authorization: Basic YmFua2VyQGdtYWlsLmNvbTpiYW5rZXI="`

- Deposit money `curl --location --request POST 'localhost:8080/clients/deposit' --header 'Authorization: Basic YmFua2VyQGdtYWlsLmNvbTpiYW5rZXI=' --header 'Content-Type: application/json' --data-raw '{
                      "amount":"10"
                  }'`

- Withdraw money `curl --location --request POST 'localhost:8080/clients/withdraw' --header 'Authorization: Basic YmFua2VyQGdtYWlsLmNvbTpiYW5rZXI=' --header 'Content-Type: application/json' --header 'Cookie: JSESSIONID=9556E1921B130DBE2519203AC4DD0EDB' --data-raw '{
                     "amount":"10"
                 }'`
                 
- Get balance `curl --location --request GET 'localhost:8080/clients/balance' --header 'Authorization: Basic YmFua2VyQGdtYWlsLmNvbTpiYW5rZXI=' `                 


