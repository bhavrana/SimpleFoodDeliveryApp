Assumptions
-----------
1. Ideally, the delivery person associated with a batch of orders should be determined at runtime. However, for simplicity, we have made the delivery person a part of the batch order our controller receives
2. The average speed can vary from one delivery person to another, but remains fixed for a particular delivery person
3. A customer has a pre-registered location associated with his/her account and can only order from that location. 
4. A restaurant can only prepare one dish with a pre-defined preparation time.
5. One batch order consists of delivery person details, along with a list of customer(who placed the order) and restaurant(from which the order was placed) pairs

Approach Overview
-----------------
1. Have constructed an adjacency list and denoted customers, restaurants and delivery agent as the vertexes of that graph.
2. Delivery agent is the root vertex of the graph.
3. Delivery vertex has only out edges to all the restaurants in the batch order received.
4. The edge between restaurant to customer vertex is not bi-directional(i.e. delivery agent cannot visit a customer before visiting the corresponding restaurant for that order)
5. Have performed customized DFS on the above-mentioned adjacency list to get the time taken for all possible valid routes a delivery agent can take.
6. Store the results in a min heap and return the min value.

Version Information
--------------------
1. java.version : 17
2. spring boot version : 2.7.5

Sample Input (Success)
----------------------
curl --location --request POST 'http://localhost:8080/v1/deliveries' \
--header 'Content-Type: application/json' \
--data-raw '{
"deliveryAgentId" : "1",
"customerToRestaurantMap": {
"1": "1",
"2": "2"

    }
}
'

Sample Output (Success)
-----------------------
{
    "FoodDelivery": {
        "visitSequence": ["D1", "R2", "R1", "C2", "C1"],
        "expectedTime": 21100.859971240057
    }
}

Sample Input (Failure)
----------------------

curl --location --request POST 'http://localhost:8080/v1/deliveries' \
--header 'Content-Type: application/json' \
--data-raw '{
"deliveryAgentId" : "5",
"customerToRestaurantMap": {
"1": "1",
"2": "2"

    }
}
'

Sample Output (Failure)
-----------------------
{
    "status": "FAILED",
    "message": "Failed to determine food delivery sequence."
}


