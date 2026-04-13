# sindi-a2a-client-example
This example showcases how to interface with a remote A2A agent.

## Prerequisites
- Java 25 and higher.
- Maven 3.9.14 and higher.
- Gemini API key.

## Building this example:
First, go into the code, change the base URL (currently on `http://localhost:8080`) to your remote A2A base url, then build this project example:

```
mvn clean install -e
```

## Running this example.
There's only 1 class, `A2AClientExample`, which has a `main` method.
If you're using an IDE, you can run it as you would a normal Java application.
Otherwise, on your shell/console, you can run it as follows:

```
java -jar target\sindi-a2a-client-example-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

This JAR is an executable JAR.

## Example output

Here's an example when interacting with the	`content-writer-agent` A2A remote agent:

```text
Apr 16, 2026 11:34:52 AM za.co.sindi.ai.a2a.client.A2ACardResolver getAgentCard
INFO: Successfully fetched agent card data from http://localhost:8080/.well-known/agent-card.json: Content Writer Agent
=== Getting Agent Card ===
Agent: Content Writer Agent
Description: An agent that can write a comprehensive and engaging piece of content based on the provided outline and high-level description of the content
Version: 1.0.0
Skills: Writes content using an outline
Streaming?: true

Apr 16, 2026 11:35:04 AM za.co.sindi.ai.a2a.client.ClientTaskManager saveTask
INFO: New task created with id: a3c39cac-a2b6-43d9-a47c-7fbb79588675
=== Task ===
ID: a3c39cac-a2b6-43d9-a47c-7fbb79588675
Context ID: 5d185862-3ac5-43a0-9ae3-b6bce55fbb00
Status: submitted
History:
	=== Message ===
	ID: 67c5282f-d525-4c2e-b0cb-7de64eb85194
	Role: user
	Task ID: a3c39cac-a2b6-43d9-a47c-7fbb79588675
	Context ID: 5d185862-3ac5-43a0-9ae3-b6bce55fbb00
	Create a LinkedIn post about getting started with Agent2Agent protocol.


=== Task ===
ID: a3c39cac-a2b6-43d9-a47c-7fbb79588675
Context ID: 5d185862-3ac5-43a0-9ae3-b6bce55fbb00
Status: submitted
History:
	=== Message ===
	ID: 67c5282f-d525-4c2e-b0cb-7de64eb85194
	Role: user
	Task ID: a3c39cac-a2b6-43d9-a47c-7fbb79588675
	Context ID: 5d185862-3ac5-43a0-9ae3-b6bce55fbb00
	Create a LinkedIn post about getting started with Agent2Agent protocol.


=== TaskStatusUpdateEvent ===
Task Id: a3c39cac-a2b6-43d9-a47c-7fbb79588675
Context ID: 5d185862-3ac5-43a0-9ae3-b6bce55fbb00
Status: submitted
Final?: false

=== Task ===
ID: a3c39cac-a2b6-43d9-a47c-7fbb79588675
Context ID: 5d185862-3ac5-43a0-9ae3-b6bce55fbb00
Status: working
History:
	=== Message ===
	ID: 67c5282f-d525-4c2e-b0cb-7de64eb85194
	Role: user
	Task ID: a3c39cac-a2b6-43d9-a47c-7fbb79588675
	Context ID: 5d185862-3ac5-43a0-9ae3-b6bce55fbb00
	Create a LinkedIn post about getting started with Agent2Agent protocol.

	=== Message ===
	ID: d8517d60-1c47-403d-8acd-355ac5626a31
	Role: agent
	Task ID: a3c39cac-a2b6-43d9-a47c-7fbb79588675
	Context ID: 5d185862-3ac5-43a0-9ae3-b6bce55fbb00
	Processing request...


=== TaskStatusUpdateEvent ===
Task Id: a3c39cac-a2b6-43d9-a47c-7fbb79588675
Context ID: 5d185862-3ac5-43a0-9ae3-b6bce55fbb00
Status: working
Final?: false

=== Task ===
ID: a3c39cac-a2b6-43d9-a47c-7fbb79588675
Context ID: 5d185862-3ac5-43a0-9ae3-b6bce55fbb00
Status: working
History:
	=== Message ===
	ID: 67c5282f-d525-4c2e-b0cb-7de64eb85194
	Role: user
	Task ID: a3c39cac-a2b6-43d9-a47c-7fbb79588675
	Context ID: 5d185862-3ac5-43a0-9ae3-b6bce55fbb00
	Create a LinkedIn post about getting started with Agent2Agent protocol.

	=== Message ===
	ID: d8517d60-1c47-403d-8acd-355ac5626a31
	Role: agent
	Task ID: a3c39cac-a2b6-43d9-a47c-7fbb79588675
	Context ID: 5d185862-3ac5-43a0-9ae3-b6bce55fbb00
	Processing request...


Artifact:
	=== Artifact ===
	ID: 0980cd94-c34d-4cbc-a95e-6b7393e8dee9
	Name: null
	Description: null
	[🚀 **The Era of Interoperable AI: How to Get Started with Agent2Agent (A2A) Protocol**

The future of AI isn't just about one "super-bot" doing everything. It’s about an ecosystem of specialized agents—researchers, coders, marketers, and analysts—seamlessly talking to one another to solve complex problems.

But for agents to collaborate, they need a common language. That’s where the **Agent2Agent (A2A) protocol** comes in.

If you’re ready to move beyond isolated prompts and start building agentic workflows that actually scale, here is your roadmap to getting started:

### 1. Understand the "Handshake"
Before agents can exchange data, they need to establish trust and identity. A2A protocols rely on standardized communication layers (often built on JSON-RPC or specialized messaging queues). 
*   **Action:** Familiarize yourself with the communication standard your framework uses (like MASCA or specific open-source A2A headers).

### 2. Define Capabilities via Discovery
An agent needs to know *what* another agent can do. In a robust A2A setup, agents "advertise" their skills.
*   **Action:** Create a clear manifest for your agent. What are its inputs? What are its outputs? What tools does it have permission to use?

### 3. Establish Secure Communication Channels
Security is non-negotiable when agents are passing sensitive data or executing code. 
*   **Action:** Implement secure authentication (like DIDs—Decentralized Identifiers) to ensure that Agent A is actually talking to Agent B and not a malicious interceptor.

### 4. Implement Request-Response & Pub/Sub Patterns
Not all communication is linear. 
*   **Direct:** "Agent B, please analyze this CSV."
*   **Broadcast:** "To all agents: I have finished the market research. Here is the summary for whoever needs it."
*   **Action:** Build logic that allows your agent to handle asynchronous responses so it doesn't "hang" while waiting for another agent to finish a task.

### 5. Test, Iterate, and Observe
The complexity of A2A grows exponentially with the number of agents. Use observability tools to trace the "chain of thought" across the network.
*   **Action:** Start with a simple two-agent loop (e.g., a Writer Agent and a Critic Agent) before moving to a multi-agent swarm.

---

**The Bottom Line:**
The transition from "Human-to-Agent" to "Agent-to-Agent" is the biggest leap in productivity we will see this decade. By mastering the A2A protocol now, you are building the infrastructure for the autonomous economy.

Are you building with A2A protocols yet? What’s the biggest challenge you’ve faced in getting agents to "talk" to each other? Let’s discuss in the comments! 👇

#AI #AgenticWorkflows #A2A #ArtificialIntelligence #SoftwareArchitecture #FutureOfWork #TechInnovation]


=== TaskArtifactUpdateEvent ===
Task Id: a3c39cac-a2b6-43d9-a47c-7fbb79588675
Context ID: 5d185862-3ac5-43a0-9ae3-b6bce55fbb00
Append?: null
Lask Chunk?: null
	=== Artifact ===
	ID: 0980cd94-c34d-4cbc-a95e-6b7393e8dee9
	Name: null
	Description: null
	[🚀 **The Era of Interoperable AI: How to Get Started with Agent2Agent (A2A) Protocol**

The future of AI isn't just about one "super-bot" doing everything. It’s about an ecosystem of specialized agents—researchers, coders, marketers, and analysts—seamlessly talking to one another to solve complex problems.

But for agents to collaborate, they need a common language. That’s where the **Agent2Agent (A2A) protocol** comes in.

If you’re ready to move beyond isolated prompts and start building agentic workflows that actually scale, here is your roadmap to getting started:

### 1. Understand the "Handshake"
Before agents can exchange data, they need to establish trust and identity. A2A protocols rely on standardized communication layers (often built on JSON-RPC or specialized messaging queues). 
*   **Action:** Familiarize yourself with the communication standard your framework uses (like MASCA or specific open-source A2A headers).

### 2. Define Capabilities via Discovery
An agent needs to know *what* another agent can do. In a robust A2A setup, agents "advertise" their skills.
*   **Action:** Create a clear manifest for your agent. What are its inputs? What are its outputs? What tools does it have permission to use?

### 3. Establish Secure Communication Channels
Security is non-negotiable when agents are passing sensitive data or executing code. 
*   **Action:** Implement secure authentication (like DIDs—Decentralized Identifiers) to ensure that Agent A is actually talking to Agent B and not a malicious interceptor.

### 4. Implement Request-Response & Pub/Sub Patterns
Not all communication is linear. 
*   **Direct:** "Agent B, please analyze this CSV."
*   **Broadcast:** "To all agents: I have finished the market research. Here is the summary for whoever needs it."
*   **Action:** Build logic that allows your agent to handle asynchronous responses so it doesn't "hang" while waiting for another agent to finish a task.

### 5. Test, Iterate, and Observe
The complexity of A2A grows exponentially with the number of agents. Use observability tools to trace the "chain of thought" across the network.
*   **Action:** Start with a simple two-agent loop (e.g., a Writer Agent and a Critic Agent) before moving to a multi-agent swarm.

---

**The Bottom Line:**
The transition from "Human-to-Agent" to "Agent-to-Agent" is the biggest leap in productivity we will see this decade. By mastering the A2A protocol now, you are building the infrastructure for the autonomous economy.

Are you building with A2A protocols yet? What’s the biggest challenge you’ve faced in getting agents to "talk" to each other? Let’s discuss in the comments! 👇

#AI #AgenticWorkflows #A2A #ArtificialIntelligence #SoftwareArchitecture #FutureOfWork #TechInnovation]


=== Task ===
ID: a3c39cac-a2b6-43d9-a47c-7fbb79588675
Context ID: 5d185862-3ac5-43a0-9ae3-b6bce55fbb00
Status: completed
History:
	=== Message ===
	ID: 67c5282f-d525-4c2e-b0cb-7de64eb85194
	Role: user
	Task ID: a3c39cac-a2b6-43d9-a47c-7fbb79588675
	Context ID: 5d185862-3ac5-43a0-9ae3-b6bce55fbb00
	Create a LinkedIn post about getting started with Agent2Agent protocol.

	=== Message ===
	ID: d8517d60-1c47-403d-8acd-355ac5626a31
	Role: agent
	Task ID: a3c39cac-a2b6-43d9-a47c-7fbb79588675
	Context ID: 5d185862-3ac5-43a0-9ae3-b6bce55fbb00
	Processing request...


Artifact:
	=== Artifact ===
	ID: 0980cd94-c34d-4cbc-a95e-6b7393e8dee9
	Name: null
	Description: null
	[🚀 **The Era of Interoperable AI: How to Get Started with Agent2Agent (A2A) Protocol**

The future of AI isn't just about one "super-bot" doing everything. It’s about an ecosystem of specialized agents—researchers, coders, marketers, and analysts—seamlessly talking to one another to solve complex problems.

But for agents to collaborate, they need a common language. That’s where the **Agent2Agent (A2A) protocol** comes in.

If you’re ready to move beyond isolated prompts and start building agentic workflows that actually scale, here is your roadmap to getting started:

### 1. Understand the "Handshake"
Before agents can exchange data, they need to establish trust and identity. A2A protocols rely on standardized communication layers (often built on JSON-RPC or specialized messaging queues). 
*   **Action:** Familiarize yourself with the communication standard your framework uses (like MASCA or specific open-source A2A headers).

### 2. Define Capabilities via Discovery
An agent needs to know *what* another agent can do. In a robust A2A setup, agents "advertise" their skills.
*   **Action:** Create a clear manifest for your agent. What are its inputs? What are its outputs? What tools does it have permission to use?

### 3. Establish Secure Communication Channels
Security is non-negotiable when agents are passing sensitive data or executing code. 
*   **Action:** Implement secure authentication (like DIDs—Decentralized Identifiers) to ensure that Agent A is actually talking to Agent B and not a malicious interceptor.

### 4. Implement Request-Response & Pub/Sub Patterns
Not all communication is linear. 
*   **Direct:** "Agent B, please analyze this CSV."
*   **Broadcast:** "To all agents: I have finished the market research. Here is the summary for whoever needs it."
*   **Action:** Build logic that allows your agent to handle asynchronous responses so it doesn't "hang" while waiting for another agent to finish a task.

### 5. Test, Iterate, and Observe
The complexity of A2A grows exponentially with the number of agents. Use observability tools to trace the "chain of thought" across the network.
*   **Action:** Start with a simple two-agent loop (e.g., a Writer Agent and a Critic Agent) before moving to a multi-agent swarm.

---

**The Bottom Line:**
The transition from "Human-to-Agent" to "Agent-to-Agent" is the biggest leap in productivity we will see this decade. By mastering the A2A protocol now, you are building the infrastructure for the autonomous economy.

Are you building with A2A protocols yet? What’s the biggest challenge you’ve faced in getting agents to "talk" to each other? Let’s discuss in the comments! 👇

#AI #AgenticWorkflows #A2A #ArtificialIntelligence #SoftwareArchitecture #FutureOfWork #TechInnovation]


=== TaskStatusUpdateEvent ===
Task Id: a3c39cac-a2b6-43d9-a47c-7fbb79588675
Context ID: 5d185862-3ac5-43a0-9ae3-b6bce55fbb00
Status: completed
Final?: true

```