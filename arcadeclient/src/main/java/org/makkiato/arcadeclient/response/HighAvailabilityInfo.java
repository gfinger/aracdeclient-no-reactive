package org.makkiato.arcadeclient.response;

public record HighAvailabilityInfo(String clustername, String leader, String electionStatus, String leaderAddress,
                                   String replicaAddresses) {}
