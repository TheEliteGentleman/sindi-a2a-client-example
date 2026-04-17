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
		A2ACardResolver resolver = new A2ACardResolver(httpClientBuilder.build(), "http://localhost:8080");
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
					printMessage(clientEventOrMessage.getRight(), 0);
				} else if (clientEventOrMessage.isLeftPresent()) { //We've received only a ClientEvent
					ClientEvent clientEvent = clientEventOrMessage.getLeft();
					if (clientEvent.task() != null) {
						printTask(clientEvent.task(), 0);
					} 
					
					if (clientEvent.updateEvent() != null) {
						UpdateEvent update = clientEvent.updateEvent();
						if (update instanceof TaskStatusUpdateEvent tsue) printTaskStatusUpdateEvent(tsue, 0);
						else if (update instanceof TaskArtifactUpdateEvent taue) printTaskArtifactUpdateEvent(taue, 0);
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
	
	private static void printMessage(final Message message, final int depth) {
		System.out.println(tabs(depth) + "=== Message ===");
		System.out.println(tabs(depth) + "ID: " + message.getMessageId());
		System.out.println(tabs(depth) + "Role: " + message.getRole());
		System.out.println(tabs(depth) + "Task ID: " + message.getTaskId());
		System.out.println(tabs(depth) + "Context ID: " + message.getContextId());
		System.out.println(tabs(depth) + Messages.getMessageText(message));
		System.out.println();
	}
	
	private static void printTask(final Task task, final int depth) {
		 System.out.println(tabs(depth) + "=== Task ===");
		 System.out.println(tabs(depth) + "ID: " + task.getId());
		 System.out.println(tabs(depth) + "Context ID: " + task.getContextId());
		 System.out.println(tabs(depth) + "Status: " + task.getStatus().state());
		 if (task.getHistory() != null && task.getHistory().length > 0) {
			 System.out.println(tabs(depth) + "History:");
			 Arrays.asList(task.getHistory()).stream().forEach(history -> printMessage(history, depth + 1));
		 }
		 
		 if (task.getArtifacts() != null) {
			 System.out.println(tabs(depth) + "Artifact:");
			 Arrays.asList(task.getArtifacts()).stream().forEach(artifact -> printArtifact(artifact, depth + 1));
		 }
		 System.out.println();
	}
	
	private static void printArtifact(final Artifact artifact, final int depth) {
		 System.out.println(tabs(depth) + "=== Artifact ===");
		 System.out.println(tabs(depth) + "ID: " + artifact.artifactId());
		 System.out.println(tabs(depth) + "Name: " + artifact.name());
		 System.out.println(tabs(depth) + "Description: " + artifact.description());
		 if (artifact.parts() != null) {
			 System.out.println(tabs(depth) + Parts.getTextParts(Arrays.asList(artifact.parts())));
		 }
		 System.out.println();
	}
	
	private static void printTaskStatusUpdateEvent(final TaskStatusUpdateEvent event, final int depth) {
		 System.out.println(tabs(depth) + "=== TaskStatusUpdateEvent ===");
		 System.out.println(tabs(depth) + "Task Id: " + event.getTaskId());
		 System.out.println(tabs(depth) + "Context ID: " + event.getContextId());
		 System.out.println(tabs(depth) + "Status: " + event.getStatus().state());
		 System.out.println(tabs(depth) + "Final?: " + event.isFinal());
		 System.out.println();
	}
	
	private static void printTaskArtifactUpdateEvent(final TaskArtifactUpdateEvent event, final int depth) {
		 System.out.println(tabs(depth) + "=== TaskArtifactUpdateEvent ===");
		 System.out.println(tabs(depth) + "Task Id: " + event.getTaskId());
		 System.out.println(tabs(depth) + "Context ID: " + event.getContextId());
		 System.out.println(tabs(depth) + "Append?: " + event.getAppend());
		 System.out.println(tabs(depth) + "Lask Chunk?: " + event.getLastChunk());
		 if (event.getArtifact() != null) printArtifact(event.getArtifact(), depth + 1);
		 System.out.println();
	}
	
	private static String tabs(final int count) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++) {
			sb.append("\t");
		}
		
		return sb.toString();
	}
}
