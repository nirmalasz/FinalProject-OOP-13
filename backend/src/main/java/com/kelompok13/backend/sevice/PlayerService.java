package com.kelompok13.backend.sevice;

import com.kelompok13.backend.model.Player;
import com.kelompok13.backend.repository.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    public boolean isUsernameExists(String username){
        return playerRepository.findByUsername(username).isPresent();
    }

    public Player createPlayer(Player player){
        if (playerRepository.existsByUsername(player.getUsername())) {
            throw new RuntimeException("Username already exists: " + player.getUsername());
        }
        playerRepository.save(player);
        return player;
    }

    public Optional<Player> getPlayerByUsername(String username) {
        return playerRepository.findByUsername(username);
    }

    public Optional<Player> getPlayerById(UUID playerId) {
        return playerRepository.findById(playerId);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player updatePlayer(UUID playerId, Player updatedPlayer) {
        Player existingPlayer = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found with ID: " + playerId));

        // cek username
        if (updatedPlayer.getUsername() != null &&
                !updatedPlayer.getUsername().equals(existingPlayer.getUsername())) {
            if (isUsernameExists(updatedPlayer.getUsername())) {
                throw new RuntimeException("Username already exists: " + updatedPlayer.getUsername());
            }
            existingPlayer.setUsername(updatedPlayer.getUsername());
        }

        // update stats jika lebih besar
        if (updatedPlayer.getHighScore() != null) {
            existingPlayer.setHighScore(updatedPlayer.getHighScore());
        }

        if (updatedPlayer.getTotalCoins() != null ) {
            existingPlayer.setTotalCoins(updatedPlayer.getTotalCoins());
        }

        if(!updatedPlayer.getStage().isEmpty() &&
            !updatedPlayer.getStage().equals(existingPlayer.getStage())){
            existingPlayer.setStage(updatedPlayer.getStage());
        }

        return playerRepository.save(existingPlayer);
    }

    public void deletePlayer(UUID playerId){
        if (!playerRepository.existsById(playerId)){
            throw new RuntimeException("Player not found with ID: " + playerId);
        }
        playerRepository.deleteById(playerId);
    }

    public void deletePlayerByUsername(String username){
        Player player = playerRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("Player not found with username: " +username));
        playerRepository.delete(player);
    }

    public Player updatePlayerStats(UUID playerId, Integer scoreValue, Integer coinsCollected,
            String stage){
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found with ID: " + playerId));
        player.updateHighScore(scoreValue);
        player.addCoins(coinsCollected);
        player.updateStage(stage);

        return playerRepository.save(player);
    }

    public List<Player> getLeaderboardByHighScore (int limit){
        return playerRepository.findTopPlayersByHighScore(limit);
    }

    public List<Player> getLeaderboardByTotalCoins(){
        return playerRepository.findAllByOrderByTotalCoinsDesc();
    }

}
