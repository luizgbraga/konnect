package com.social_network.server.utils;

import com.social_network.server.entities.ConnectsTo;

import java.util.*;

public class Graph {
    private Map<String, List<String>> adjacencyList;

    public Graph(List<ConnectsTo> connections) {
        adjacencyList = new HashMap<>();
        buildAdjacencyList(connections);
    }

    public List<List<String>> findKn(int n) {
        List<List<String>> result = new ArrayList<>();
        List<String> current = new ArrayList<>();
        for (String nodeId : adjacencyList.keySet()) {
            current.add(nodeId);
            findCompleteSubgraphs(nodeId, n, 1, current, result);
            current.clear();
        }
        return result;
    }

    private void findCompleteSubgraphs(String currentNode, int n, int depth, List<String> current, List<List<String>> result) {
        if (depth == n) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (String neighbor : adjacencyList.getOrDefault(currentNode, new ArrayList<>())) {
            if (!current.contains(neighbor)) {
                current.add(neighbor);
                findCompleteSubgraphs(neighbor, n, depth + 1, current, result);
                current.remove(current.size() - 1);
            }
        }
    }

    private void buildAdjacencyList(List<ConnectsTo> connections) {
        for (ConnectsTo connection : connections) {
            String userFromId = connection.getUserFromId();
            String userToId = connection.getUserToId();

            // Add userToId to the adjacency list of userFromId
            if (!adjacencyList.containsKey(userFromId)) {
                adjacencyList.put(userFromId, new ArrayList<>());
            }
            adjacencyList.get(userFromId).add(userToId);

            // Add userFromId to the adjacency list of userToId
            if (!adjacencyList.containsKey(userToId)) {
                adjacencyList.put(userToId, new ArrayList<>());
            }
            // Since it's a graph, you might want to add both directions
            // If it's a directed graph, you can skip adding userFromId to userToId's list
            adjacencyList.get(userToId).add(userFromId);
        }
    }

    public List<NodeDepthPair> findNodesWithinDepthRange(String startNodeId, int minDepth, int maxDepth) {
        List<NodeDepthPair> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        dfs(startNodeId, 0, minDepth, maxDepth, visited, result);
        return result;
    }

    private void dfs(String nodeId, int depth, int minDepth, int maxDepth, Set<String> visited, List<NodeDepthPair> result) {
        if (depth >= minDepth && depth <= maxDepth) {
            result.add(new NodeDepthPair(nodeId, depth));
        }
        visited.add(nodeId);
        if (depth < maxDepth) {
            for (String neighbor : adjacencyList.getOrDefault(nodeId, Collections.emptyList())) {
                if (!visited.contains(neighbor)) {
                    dfs(neighbor, depth + 1, minDepth, maxDepth, visited, result);
                }
            }
        }
    }

    public static class NodeDepthPair {
        private String nodeId;
        private int depth;

        public NodeDepthPair(String nodeId, int depth) {
            this.nodeId = nodeId;
            this.depth = depth;
        }

        public String getNodeId() {
            return nodeId;
        }

        public int getDepth() {
            return depth;
        }
    }
}
