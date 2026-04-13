/**
 * 
 */
package za.co.sindi.a2a.example;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import za.co.sindi.ai.a2a.client.A2ACardResolver;
import za.co.sindi.ai.a2a.client.Client;
import za.co.sindi.ai.a2a.client.ClientConfig.ClientConfigBuilder;
import za.co.sindi.ai.a2a.client.ClientEvent;
import za.co.sindi.ai.a2a.client.ClientFactory;
import za.co.sindi.ai.a2a.client.Helpers;
import za.co.sindi.ai.a2a.types.AgentCard;
import za.co.sindi.ai.a2a.types.AgentSkill;
import za.co.sindi.ai.a2a.types.Artifact;
import za.co.sindi.ai.a2a.types.Message;
import za.co.sindi.ai.a2a.types.Task;
import za.co.sindi.ai.a2a.types.TaskArtifactUpdateEvent;
import za.co.sindi.ai.a2a.types.TaskStatusUpdateEvent;
import za.co.sindi.ai.a2a.types.TransportProtocol;
import za.co.sindi.ai.a2a.types.UpdateEvent;
import za.co.sindi.ai.a2a.utils.Messages;
import za.co.sindi.ai.a2a.utils.Parts;

/**
 * @author Buhake Sindi
 * @since 06 April 2026
 */
public final class A2AClientExample {
	
	private static Client getClient(final AgentCard agentCard, final HttpClient.Builder httpClientBuilder) {
		ClientConfigBuilder clientConfigBuilder = new ClientConfigBuilder();
		clientConfigBuilder.httpClient(httpClientBuilder.build())
						   .supportedTransports(TransportProtocol.values());
		ClientFactory clientFactory = new ClientFactory(clientConfigBuilder.build());
		return clientFactory.create(agentCard);
	}

	public static void main(String[] args) {
		HttpClient.Builder httpClientBuilder = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(30));
		A2ACardResolver resolver = new A2ACardResolver(httpClientBuilder.build(), "http://localhost:9080");
		AgentCard agentCard = resolver.getAgentCard();
		
		// Example 1: Get agent card
        System.out.println("=== Getting Agent Card ===");
        System.out.println("Agent: " + agentCard.getName());
        System.out.println("Description: " + agentCard.getDescription());
        System.out.println("Version: " + agentCard.getVersion());
        System.out.println("Skills: " + stringify(agentCard.getSkills()).trim());
        System.out.println("Streaming?: " + agentCard.getCapabilities().streaming());
        System.out.println();
        
        Client client = getClient(agentCard, httpClientBuilder);
		client.addEventConsumer((clientEventOrMessage, _) -> {
			if (clientEventOrMessage != null) {
				if (clientEventOrMessage.isRightPresent()) { //Meaning we can only receive a Message
					printMessage(clientEventOrMessage.getRight());
				} else if (clientEventOrMessage.isLeftPresent()) { //We've received only a ClientEvent
					ClientEvent clientEvent = clientEventOrMessage.getLeft();
					if (clientEvent.task() != null) {
						printTask(clientEvent.task());
					} 
					
					if (clientEvent.updateEvent() != null) {
						UpdateEvent update = clientEvent.updateEvent();
						if (update instanceof TaskStatusUpdateEvent tsue) printTaskStatusUpdateEvent(tsue);
						else if (update instanceof TaskArtifactUpdateEvent taue) printTaskArtifactUpdateEvent(taue);
					}
				}
			}
		});
		
//		Message userMessage = new Message(Role.USER, new Part[] {new TextPart("Create a LinkedIn post about getting started with Agent2Agent protocol.")}, UUID.randomUUID().toString());
		client.sendMessage(Helpers.createTextMessageObject("Create a LinkedIn post about getting started with Agent2Agent protocol."));
	}
	
	private static String stringify(final AgentSkill[] skills) {
		List<String> skillNames = Arrays.asList(skills).stream().map(skill -> skill.getName()).toList();
		return String.join(", ", skillNames);
	}
	
	private static void printMessage(final Message message) {
		System.out.println("=== Message ===");
		System.out.println("ID: " + message.getMessageId());
		System.out.println("Role: " + message.getRole());
		System.out.println("Task ID: " + message.getTaskId());
		System.out.println("Context ID: " + message.getContextId());
		System.out.println(Messages.getMessageText(message));
		System.out.println();
	}
	
	private static void printTask(final Task task) {
		 System.out.println("=== Task ===");
		 System.out.println("ID: " + task.getId());
		 System.out.println("Context ID: " + task.getContextId());
		 System.out.println("Status: " + task.getStatus().state());
		 if (task.getHistory() != null && task.getHistory().length > 0) {
			 System.out.println("<<< History >>>");
			 Arrays.asList(task.getHistory()).stream().forEach(history -> printMessage(history));
			 System.out.println("--- History ---");
		 }
		 System.out.println();
	}
	
	private static void printArtifact(final Artifact artifact) {
		 System.out.println("=== Artifact ===");
		 System.out.println("ID: " + artifact.artifactId());
		 System.out.println("Name: " + artifact.name());
		 System.out.println("Description: " + artifact.description());
		 if (artifact.parts() != null) {
			 System.out.println(Parts.getTextParts(Arrays.asList(artifact.parts())));
		 }
		 System.out.println();
	}
	
	private static void printTaskStatusUpdateEvent(final TaskStatusUpdateEvent event) {
		 System.out.println("=== TaskStatusUpdateEvent ===");
		 System.out.println("Task Id: " + event.getTaskId());
		 System.out.println("Context ID: " + event.getContextId());
		 System.out.println("Status: " + event.getStatus().state());
		 System.out.println("Final?: " + event.isFinal());
		 System.out.println();
	}
	
	private static void printTaskArtifactUpdateEvent(final TaskArtifactUpdateEvent event) {
		 System.out.println("=== TaskArtifactUpdateEvent ===");
		 System.out.println("Task Id: " + event.getTaskId());
		 System.out.println("Context ID: " + event.getContextId());
		 System.out.println("Append?: " + event.getAppend());
		 System.out.println("Lask Chunk?: " + event.getLastChunk());
		 if (event.getArtifact() != null) printArtifact(event.getArtifact());
		 System.out.println();
	}
}
