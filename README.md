# Ford-Fulkerson Algorithm Analysis

This project implements and analyzes the Ford-Fulkerson algorithm variations for solving maximum flow problems in source-sink networks. Four variations, including Shortest Augmenting Path (SAP), DFS-Like, Random DFS, and Maximum Capacity (Max Cap), are examined under different graph conditions.


## Introduction

The project focuses on the Ford-Fulkerson algorithm variations, evaluating their performance in terms of paths, mean length (ML), mean proportional length (MPL), and total edges. The variations are tested on source-sink graphs generated with specified parameters such as the number of vertices (n), Euclidean distance threshold (r), and upper capacity limit (upperCap).

## Graph Generation

The `GenerateSourceSinkGraph` class creates a random source-sink graph based on given parameters. It uses these parameters to generate random coordinates for vertices, create edges within the Euclidean distance threshold, and assign capacities to the edges. The generated graph is saved in a CSV file.

## Ford Fulkerson Algorithms 

The class FordFulkersonAlgorithmRunner, has all 4 algorithms variations to find augmenting paths in the normal Ford fulkerson algorithm.

## CSV files   
SourceSinkGraph1.csv : It is a csv file for the first values out of the 8 Simulation I simulations professor gave, Similarly SourceSinkGraph2.csv has it for second simulation variation of Simulation I. Finally SourceSinkGraph9.csv and SourceSinkGraph10.csv are the simulation values for Simulation II from the results I found in Simulation I.

## Compilation

Compile the Java source code using the following command:

```bash
javac GenerateSourceSinkGraph.java FordFulkersonAlgorithmRunner.java
```

## Usage

Run the compiled Java program to generate a source-sink graph and execute the Ford-Fulkerson algorithm variations:

```bash
java GenerateSourceSinkGraph
java FordFulkersonAlgorithmRunner
```

How to Run the Algorithm :

There are total 2 files in the algorithm the first file is GenerateSourceSinkGraph and other is FordFulkersonAlgorithmRunner.

First you should run the GenerateSourceSinkGraph with the values of n, r, and upperCap you want in the main method along with the csv file name you want the grpah to be stored in and then run the FordFulkersonAlgorithmRunner with the same csv file name in the readDataFromCsv method. Also if you want to check my readings from the prerecorded graph just change the file path in FordFulkersonAlgorithmRunner to that particular csv file name in the readDataFromCsv method.
