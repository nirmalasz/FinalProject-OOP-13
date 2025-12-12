package com.kelompok13.backend.controller;

import com.kelompok13.backend.model.Player;
import com.kelompok13.backend.sevice.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

@RestController //menangani req http lalu tulis ke json
@RequestMapping("/api/players") //menandakan endpoint
@CrossOrigin(origins = "*")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    //endpoint get
    //GET /api/players
    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers(){
        List<Player> players = playerService.getAllPlayers();
        return ResponseEntity.ok(players);
    }

    //GET /api/players/{playerId}
    @GetMapping("/{playerId}")
    public ResponseEntity<?> getPlayerId(@PathVariable UUID playerId){
        Optional<Player> player = playerService.getPlayerById(playerId);

        if (player.isPresent()){
            return ResponseEntity.ok(player.get());
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"ID player tidak ditemukan: " + playerId + "\"}");
        }

    }

    //GET /api/players/username/{username}
    @GetMapping("/username/{username}")
    public ResponseEntity<?> getPlayerUsername(@PathVariable String username){
        Optional<Player> player = playerService.getPlayerByUsername(username);
        if (player.isPresent()){
            return ResponseEntity.ok(player.get());
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(("{\"error\": \""  + username + "\"}"));
        }
    }

    //GET /api/players/check-username/{username}
    @GetMapping("/check-username/{username}")
    public ResponseEntity<?> checkUsername(@PathVariable String username){
        boolean usernameExist = playerService.isUsernameExists(username);
        return ResponseEntity.ok("{\"exists\": " + usernameExist + "\"}");
    }

    //endpoint post
    //POST /api/players
    @PostMapping
    public ResponseEntity<?> createPlayer(@RequestBody Player player){
        try{
            Player newPlayer = playerService.createPlayer(player);
            return ResponseEntity.status(HttpStatus.CREATED).body(newPlayer);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    //end point put & delete
    //put
    @PutMapping("/{playerId}")
    public  ResponseEntity<?> updatePlayer(@PathVariable UUID playerId,@RequestBody Player player){
        try {
            Player updatedPlayer = playerService.updatePlayer(playerId, player);
            return ResponseEntity.ok(updatedPlayer);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body("{\"error\": \""  + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/username/{username}")
    public ResponseEntity<?> updatePlayerByUsername(@PathVariable String username, @RequestBody Player player) {
        try {
            Optional<Player> existingPlayer = playerService.getPlayerByUsername(username);
            if (existingPlayer.isPresent()) {
                Player updatedPlayer = playerService.updatePlayer(existingPlayer.get().getPlayerId(), player);
                return ResponseEntity.ok(updatedPlayer);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\": \"Player not found with username: " + username + "\"}");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    //delete
    @DeleteMapping("/{playerId}")
    public  ResponseEntity<?> deletePlayer(@PathVariable UUID playerId){
        try {
            playerService.deletePlayer(playerId);
            return ResponseEntity.ok("Player berhasil dihapus");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \""  + e.getMessage()+ "\"}");
        }
    }

    @DeleteMapping("/username/{username}")
    public ResponseEntity<?> deletePlayerByUsername(@PathVariable String username) {
        try {
            playerService.deletePlayerByUsername(username);
            return ResponseEntity.ok("{\"message\": \"Player deleted successfully\"}");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    //end point leaderboard
    //leaderboard high score
    @GetMapping("/leaderboard/high-score")
    public  ResponseEntity<List<Player>> getLeaderboardByHighScore(@RequestParam(defaultValue = "10") int
            limit){
        List<Player> players = playerService.getLeaderboardByHighScore(limit);
        return ResponseEntity.ok(players);
    }

    //leaderboard total coin
    @GetMapping("/leaderboard/total-coins")
    public  ResponseEntity<List<Player>> getLeaderboardByTotalCoins(@RequestParam(defaultValue =
            "10") int
            limit){
        List<Player> players = playerService.getLeaderboardByTotalCoins();
        return ResponseEntity.ok(players);
    }
}
