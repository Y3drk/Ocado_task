# Bot Route Planner Task
Here I will sum up my solution of the problem, describe its time complexity and answer the question from the additional task.

# Silent presuppositions  
As it wasn't described in the task file I assumed that:

 - Provided data is always correct
 - Layers in some modules can be empty and in case that happens the containers stay on the level that was indicated in the file

Also

 - In my solution I used constant 99999 defined as Infinity assuming that this value will never be reached as a result of the task. It can of course be increased if necessary.
 - In my time complexity descriptions **V = XY** where X and Y are the sizes of the Grid in respective dimensions. Depending on the context I will use both notations
 - I've decided to unit test both parsers and the Floyd-Warshall algorithm as those are in my opinion, key components of the task

#Solution algorithm
To solve the given task I followed these steps:

 1. Parse both files. When parsing grid-x.txt create an array of modules with containers and an array representing the graph, where the vertices are modules and edges are times of the bot sliding between them.
 2. Run the Floyd-Warshall algorithm on the mentioned above graph, to create the shortest paths from every vertex to every other one if only it's reachable of course. That way we will have all the necessary path times at hand. The algorithm will also create parents array which will allow us to recreate the path of the bot later on.
 3. Create an array of results of the same size as the modules array. Iterate through it and on every module that has the product that we want put the result of the formula  
**time = 1P + G + 2P**  
 where:
 - 1P = time of pathing from start to the module
 - G = time of getting the product
 - 2P = time of pathing from the module to the receiving station

 4. Iterate through results array once more and find the shortest time of the performed task.
 5. Recreate the path of the bot using the mentioned parents array.
 6. Print the results in the required format

# Time complexity
 1. Parsing the files is performed with the jdk provided solutions, so I won't dive into it because I don't know it that well.  
creation of the arrays takes **O(1)** for modules as it is performed while parsing the file and **O(XY)** for the graph since we have to consider all modules as vertices.
 2. The Floyd-Warshall algorithm has a time complexity of **O(V^3)**. I've been thinking about using the Dijkstra's shortest path algorithm, but it would require rerunning it from all the modules that contain the desired product, so in the end it would have around **O(VElog(V))** complexity, where **E~6V**, so the profit wouldn't be worth it as the solution would get much messier and more complex.  
I wanted to keep it simple if a bit slower.
 3. Iterating through results array is **O(2XY)**.

# Additional Task
 Another advantage of using the Floyd-Warshall algorithm is that the necessary change is very simple to implement.  
 We would only have to consider all the receiving stations when iterating through results array for the first time (step 3 of the solution), by simply trying different paths, changing the 2P part of the formula and looking for the minimal value of it.  
 The rest of the solution basically stays the same, excluding some parser changes depending on how the stations would be described in one of the files.  
 In case of the time complexity cost, the change would require adding 1 more for loop inside the iteration. Therefore, the complexity of step 3 would go up to **O(XYW + XY)** where **W = all receiving stations**, so possibly **W = XY**. Then the complexity is **O([XY]^2 + XY)**.   
 It doesn't affect the total complexity though as the Floyd-Warshall algorithm is still more time-consuming.