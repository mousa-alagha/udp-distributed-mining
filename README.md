# Distributed Mining System (UDP + Load Balancer)

This project implements a scalable and secure distributed mining system using the **UDP protocol**, later extended with a **Load Balancer** architecture.

## 📁 Project Structure

### Part 1 – Basic UDP Mining System

| File | Description |
|------|-------------|
| Block_Part1_22_5.java | Data model representing a mining block (number, nonce, data, and hash). |
| Client_Part1_22_5.java | UDP client that sends mining requests to the server. |
| Server_Part1_22_5.java | UDP server that performs mining by finding a suitable nonce. |
| StringUtil_Part1_22_5.java | Utility for hashing and checking hash difficulty (number of leading zeros). |

### Part 2 – Load-Balanced UDP Mining System

| File | Description |
|------|-------------|
| Block_Part2_22_5.java | Extended block model compatible with the load-balanced system. |
| Client_Part2_22_5.java | Client that sends mining jobs through the Load Balancer. |
| LoadBalancer_Part2_22_5.java | Distributes mining ranges to multiple servers. |
| Server_Part2_22_5.java | Performs mining on assigned nonce ranges. |
| StringUtil_Part2_22_5.java | Utility reused for consistent hash validation. |

## 🚀 Features

- Simple and lightweight **UDP-based client-server** architecture.
- **Mining simulation** by finding nonce values for hashes with specified leading zeros.
- **Load balancing** via a custom module that divides mining ranges among multiple servers.
- **Performance analysis** of execution time across clients and difficulty levels.

## 📊 Performance Analysis

The system has been tested with multiple clients, variable difficulty (leading zeros), and different numbers of servers. Results showed significant improvements with load balancing.

## 🛠️ How to Run

1. Compile all Java files.
2. Run the server(s) and load balancer in appropriate order.
3. Run multiple clients to simulate mining requests.
4. Monitor the console for execution time and mining results.

