Deployment
----

cd project path

run mvn clean install

run java -jar target/markSix.jar

Improvement
----

The limitation of the program is performance issue when the contestants got many tickets

the ticket pool list will become bigger, then the checking of the winner will be slow due to it is a looping

for check the result one by one. 

I will improve it by adding a Database for persist the data if I have time. Then do not need to loop

the ticket list of the contestants, and it can directly get the result by one query for response.

The performance should be enhanced when the tickets list of contestants are huge.