<!DOCTYPE html>
<html>
<head>
  <title>kalaha Board</title>
  <style>
    table {
      border-collapse: collapse;
      margin: 20px auto;
    }
    td {
      width: 80px;
      height: 80px;
      text-align: center;
      border: 1px solid black;
      font-size: 24px;
    }
    .player-a {
      background-color: #FFD700;
    }
    .player-b {
      background-color: #ADD8E6;
    }
    .big-pit {
      font-size: 32px;
      background-color: #FFA500;
    }
  </style>
</head>
<body>
  <h1>kalaha Board</h1>

  <h2>Board Setup</h2>
  <p>Each of the two players has his six pits in front of him. To the right of the six pits, each player has a larger pit. At the start of the game, there are six stones in each of the six round pits.</p>

  <h2>Rules</h2>
  <h3>Game Play</h3>
  <p>The player who begins with the first move picks up all the stones in any of his own six pits and sows the stones on to the right, one in each of the following pits, including his own big pit. No stones are put in the opponents' big pit. If the player's last stone lands in his own big pit, he gets another turn. This can be repeated several times before it's the other player's turn.</p>
  
  <h3>Capturing Stones</h3>
  <p>During the game, the pits are emptied on both sides. Always when the last stone lands in an own empty pit, the player captures his own stone and all stones in the opposite pit (the other player’s pit) and puts them in his own big pit.</p>
  
  <h3>The Game Ends</h3>
  <p>The game is over as soon as one of the sides runs out of stones. The player who still has stones in his pits keeps them and puts them in his big pit. The winner of the game is the player who has the most stones in his big pit.</p>


<form id="gameForm">
        <button type="button" onclick="initGame()">Start Game</button>
        <br><br>      
    </form>

<div >
	
  <table id="gameTable">
    <tr>
      <td></td>
      <td class="player-b">6</td>
      <td class="player-b">5</td>
      <td class="player-b">4</td>
      <td class="player-b">3</td>
      <td class="player-b">2</td>
      <td class="player-b">1</td>
      <td class="player-b big-pit">0</td>
    </tr>
    <tr>
      <td class="player-a big-pit">0</td>
      <td class="player-a">1</td>
      <td class="player-a">2</td>
      <td class="player-a">3</td>
      <td class="player-a">4</td>
      <td class="player-a">5</td>
      <td class="player-a">6</td>
      <td></td>
    </tr>
  </table>
  
  </div>
  
  <%<script>
  
  	function updateGameTable(game) {
            var playerAStones = game.playerA.pits;
            var playerBStones = game.playerB.pits;
            var playerABigPit = game.playerA.bigPit;
            var playerBBigPit = game.playerB.bigPit;

            // Update player A's pits
            for (var i = 0; i < playerAStones.length; i++) {
                var pit = playerAStones[i];
                var pitElement = $('#gameTable .player-a').eq(i);
                pitElement.text(pit);
            }
            // Update player A's big pit
            $('#gameTable .player-a.big-pit').text(playerABigPit);

            // Update player B's pits
            for (var i = 0; i < playerBStones.length; i++) {
                var pit = playerBStones[i];
                var pitElement = $('#gameTable .player-b').eq(i);
                pitElement.text(pit);
            }
            // Update player B's big pit
            $('#gameTable .player-b.big-pit').text(playerBBigPit);
        }
        function initGame() {
            $.ajax({
                url: 'localhost:8080/kalaha',
                type: 'GET',
                success: function(response) {
                    console.log('Game initialized:', response);
                     updateGameTable(response);
                },
                error: function(xhr, status, error) {
                    console.error('Error initializing game:', error);
                }
            });
        }

        function playMove() {
            var gameInput = $('#gameInput').val();
            $.ajax({
                url: '/your-backend-endpoint/playMove',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({ move: gameInput }),
                success: function(response) {
                    console.log('Move played:', response);
                      updateGameTable(response);
                },
                error: function(xhr, status, error) {
                    console.error('Error playing move:', error);
                }
            });
        }
    </script>

</body>
</html>