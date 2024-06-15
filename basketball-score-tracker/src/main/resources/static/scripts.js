let gameStatusInterval;

async function startGame() {
    const homeTeamName = prompt('Enter home team name:');
    const awayTeamName = prompt('Enter away team name:');
    const duration = prompt('Enter game duration in seconds:');

    console.log('Starting game with parameters:', { homeTeamName, awayTeamName, duration });

    try {
        const response = await fetch(`/api/game/start?homeTeamName=${homeTeamName}&awayTeamName=${awayTeamName}&durationInSeconds=${duration}`, { method: 'POST' });
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const game = await response.json();
        document.getElementById('gameStatus').innerText = 'Game Started';
        updateScoreboard(game);
        startGameStatusInterval();
    } catch (error) {
        console.error('Error starting game:', error);
    }
}

async function pauseGame() {
    try {
        const response = await fetch('/api/game/pause', { method: 'POST' });
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const game = await response.json();
        document.getElementById('gameStatus').innerText = 'Game Paused';
        clearInterval(gameStatusInterval);
    } catch (error) {
        console.error('Error pausing game:', error);
    }
}

async function resumeGame() {
    try {
        const response = await fetch('/api/game/resume', { method: 'POST' });
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const game = await response.json();
        document.getElementById('gameStatus').innerText = 'Game Resumed';
        startGameStatusInterval();
    } catch (error) {
        console.error('Error resuming game:', error);
    }
}

async function updateScore() {
    const homeScore = prompt('Enter home team score:');
    const awayScore = prompt('Enter away team score:');

    try {
        const response = await fetch(`/api/game/update?homeScore=${homeScore}&awayScore=${awayScore}`, { method: 'POST' });
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const game = await response.json();
        updateScoreboard(game);
    } catch (error) {
        console.error('Error updating score:', error);
    }
}

function updateScoreboard(game) {
    document.getElementById('homeTeamName').innerText = game.homeTeamName;
    document.getElementById('awayTeamName').innerText = game.awayTeamName;
    document.getElementById('homeScore').innerText = game.homeTeamScore;
    document.getElementById('awayScore').innerText = game.awayTeamScore;
    document.getElementById('timeRemaining').innerText = game.timeRemainingFormatted;
}

async function fetchGameStatus() {
    try {
        const response = await fetch('/api/game/status');
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const game = await response.json();
        updateScoreboard(game);
    } catch (error) {
        console.error('Error fetching game status:', error);
    }
}

function startGameStatusInterval() {
    clearInterval(gameStatusInterval); // Clear any existing interval
    gameStatusInterval = setInterval(fetchGameStatus, 1000); // Fetch game status every second
}

