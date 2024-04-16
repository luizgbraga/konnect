package com.social_network.server.utils;

import com.social_network.server.entities.ConnectsTo;

import java.util.*;

public class Graph {
    private Map<byte[], List<byte[]>> adjacencyList;

    public Graph(List<ConnectsTo> connections) {
        adjacencyList = new HashMap<>();
        buildAdjacencyList(connections);
    }

    private void buildAdjacencyList(List<ConnectsTo> connections) {
        for (ConnectsTo connection : connections) {
            byte[] userFromId = connection.getUserFromId();
            byte[] userToId = connection.getUserToId();

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

    public List<NodeDepthPair> findNodesWithinDepthRange(byte[] startNodeId, int minDepth, int maxDepth) {
        List<NodeDepthPair> result = new ArrayList<>();
        Set<byte[]> visited = new HashSet<>();
        dfs(startNodeId, 0, minDepth, maxDepth, visited, result);
        return result;
    }

    private void dfs(byte[] nodeId, int depth, int minDepth, int maxDepth, Set<byte[]> visited, List<NodeDepthPair> result) {
        if (depth >= minDepth && depth <= maxDepth) {
            result.add(new NodeDepthPair(nodeId, depth));
        }
        visited.add(nodeId);
        if (depth < maxDepth) {
            for (byte[] neighbor : adjacencyList.getOrDefault(nodeId, Collections.emptyList())) {
                if (!visited.contains(neighbor)) {
                    dfs(neighbor, depth + 1, minDepth, maxDepth, visited, result);
                }
            }
        }
    }

    public static class NodeDepthPair {
        private byte[] nodeId;
        private int depth;

        public NodeDepthPair(byte[] nodeId, int depth) {
            this.nodeId = nodeId;
            this.depth = depth;
        }

        public byte[] getNodeId() {
            return nodeId;
        }

        public int getDepth() {
            return depth;
        }
    }
}
