# Ford-Fulkerson Algorithm Analysis

This project implements and analyzes the Ford-Fulkerson algorithm variations for solving maximum flow problems in source-sink networks. Four variations, including Shortest Augmenting Path (SAP), DFS-Like, Random DFS, and Maximum Capacity (Max Cap), are examined under different graph conditions.

## Table of Contents
- [Introduction](#introduction)
- [Graph Generation](#graph-generation)
- [Compilation](#compilation)
- [Usage](#usage)
- [Results and Analysis](#results-and-analysis)
- [Conclusion](#conclusion)
- [References](#references)

## Introduction

The project focuses on the Ford-Fulkerson algorithm variations, evaluating their performance in terms of paths, mean length (ML), mean proportional length (MPL), and total edges. The variations are tested on source-sink graphs generated with specified parameters such as the number of vertices (n), Euclidean distance threshold (r), and upper capacity limit (upperCap).

## Graph Generation

The `GenerateSourceSinkGraph` class creates a random source-sink graph based on given parameters. It uses these parameters to generate random coordinates for vertices, create edges within the Euclidean distance threshold, and assign capacities to the edges. The generated graph is saved in a CSV file.

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

This will generate a source-sink graph CSV file and perform algorithm simulations.

## Results and Analysis

The `FordFulkersonAlgorithmRunner` class reads the generated graph CSV file and executes the Ford-Fulkerson algorithm variations. The metrics, including paths, ML, MPL, and total edges, are printed for each algorithm. Results are displayed in a formatted table.

## Conclusion

The conclusion section provides insights into the strengths and weaknesses of each Ford-Fulkerson algorithm variation based on the analysis of performance metrics. Recommendations for choosing algorithms based on network characteristics are included.

## References

References for algorithm variations and analysis are documented within the source code.

Feel free to customize this README according to your project structure and specific details. Adjust paths, commands, and additional instructions based on your requirements.
