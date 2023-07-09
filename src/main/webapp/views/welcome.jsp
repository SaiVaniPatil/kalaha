<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!DOCTYPE html>
<html>
<head>
  <title>kalaha Board</title>
  <style>
  h1 {
      text-align: center;
    }
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
     
    }
    .turn {
      font-size: 32px;
      background-color: ##FF0000;
    }
  </style>
</head>
<body>
  <h1>Kalaha Board</h1>

  <h2>Board Setup</h2>
  <p>Each of the two players has his six pits in front of him. To the right of the six pits, each player has a larger pit. At the start of the game, there are six stones in each of the six round pits.</p>

  <h2>Rules</h2>
  <p>The player who begins with the first move picks up all the stones in any of his own six pits and sows the stones on to the right, one in each of the following pits, including his own big pit. No stones are put in the opponents' big pit. If the player's last stone lands in his own big pit, he gets another turn. 
  This can be repeated several times before it's the other player's turn.
  Player cannot choose big pit,empty Pit and opponent's pit.</p>
 
  
  <h3>Capturing Stones</h3>
  <p>During the game, the pits are emptied on both sides. Always when the last stone lands in an own empty pit, the player captures his own stone and all stones in the opposite pit (the other player pit) and puts them in his own big pit.</p>
  
  <h3>The Game Ends</h3>
  <p>The game is over as soon as one of the sides runs out of stones. The player who still has stones in his pits keeps them and puts them in his big pit. The winner of the game is the player who has the most stones in his big pit.</p>


<form id="gameForm">
        <button type="button" onclick="initGame()">Start New Game</button>
        <br><br>      
    </form>
    
    <div class="turn"> </div>

<div id="kalahaboard"  style="display: none;">

	<table id="Winner">
	</table>
	
	<div id ="game">
	
	
  <table id="gameTable">
  <tr> <td class="player-b-name">Player 2</td></tr>
    <tr>
    
      <td class="player-b big-pit" id="14">0</td>
      <td class="player-b" onclick="playMove(1,13)"  id="13">6</td>
      <td class="player-b" onclick="playMove(1,12)" id="12">5</td>
      <td class="player-b" onclick="playMove(1,11)" id="11">4</td>
      <td class="player-b" onclick="playMove(1,10)" id="10">3</td>
      <td class="player-b" onclick="playMove(1,9)" id="9">2</td>
      <td class="player-b" onclick="playMove(1,8)" id="8">1</td>
      <td></td> 
      
    </tr>
    <tr>
      <td></td>
      <td class="player-a"  onclick="playMove(0,1)" id ="1" >1</td>
      <td class="player-a"  onclick="playMove(0,2)" id="2">2</td>
      <td class="player-a"  onclick="playMove(0,3)" id="3">3</td>
      <td class="player-a"  onclick="playMove(0,4)" id="4">4</td>
      <td class="player-a"  onclick="playMove(0,5)" id="5">5</td>
      <td class="player-a"  onclick="playMove(0,6)" id="6">6</td>
      <td class="player-a big-pit" id="7">0</td>
     
    </tr>
    <tr> <td class="player-a-name">Player 1</td></tr>
  </table>
  
  </div>
  
  </div>
  
  
  
  <script>
  
    var gameId;
    var currPlayerIndex;
     
  
  	function updateGameTable(game) {  	
  			
  	
            var player0Stones = game.pits.slice(0, 7);
            var player1Stones = game.pits.slice(7, 14);
            var player0BigPit = game.pits[6];
            var player1BigPit = game.pits[13];
            
            
            gameId = game.id;
            
            currPlayerIndex = game.currPlayerIndex;
           

            // Update player 0's pits
            for (var i = 0; i < player0Stones.length; i++) {
            
                var pitStones = player0Stones[i].noOfStones;
                var id =i+1;
                var pitElement = $('#gameTable .player-a').eq(i);
                pitElement.text(pitStones); 
            }
            
            
            // Update player 1's big pit
            //$('#gameTable .player-a.big-pit').text(player0BigPit.noOfStones);

            // Update player B's pits
            for (var i = 1; i < player1Stones.length; i++) {
                var pitStones = player1Stones[i-1].noOfStones;
                var pitElement = $('#gameTable .player-b').eq(player1Stones.length-i);
                pitElement.text(pitStones);
            }
            // Update player B's big pit
            $('#gameTable .player-b.big-pit').text(player1BigPit.noOfStones);
            
            
           var name = game.players[currPlayerIndex].name;
           
           var msg = name +" "+ " turn to play"
          
          $('.turn').text(msg); 
          
          
          if(game.gameEnded)
                      {
                      
                       	document.getElementById('gameForm').style.display = 'block';
                    
                    
                    	document.getElementById('kalahaboard').style.display = 'none';
                    	
                    	                    
                    	
                    	var gameWinner = game.winnerNames + " won the game";
                    	
                    	
                    	
                    	   $('.turn').text(gameWinner);  
                    	
                    
                      } 
          
          }        
          
          
        
        
        function initGame() {
            $.ajax({
                url: 'http://localhost:8090/v1/kalaha',
                type: 'POST',
                success: function(response) {
                
               
               
                    console.log('Game initialized v3:', response);
                    
                     document.getElementById('gameForm').style.display = 'none';
                    
                    
                    document.getElementById('kalahaboard').style.display = 'block';
                    
                    updateGameTable(response);
                   
                },
                error: function(xhr, status, error) {
                    console.error('Error initializing game:', error);
                }
            });
        }
        
        
         function playMove(playerId,pitNum) {
         
       // alert(playerId);
         
        // alert(currPlayerIndex);
        
	        if(playerId == currPlayerIndex)
	        {
	        	movePit(pitNum);
	        }
        }
        
        
        function movePit(pitNum) {
        
         //alert(pitNum + "in playmove");
        
        
        var gameInput = {
    "gameId": gameId,
    "pitNum": pitNum
};
        
        
            
            $.ajax({
                url: 'http://localhost:8090/v1/kalaha/play',
                type: 'PATCH',
                contentType: 'application/json',
                data: JSON.stringify( gameInput ),
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