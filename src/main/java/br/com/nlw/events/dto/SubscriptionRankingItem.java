package br.com.nlw.events.dto;

public record SubscriptionRankingItem(Long subscription, Integer userId, String name) {
    // Tudo que é quantidade JPA pede pra ser Long
}
