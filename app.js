let currentMovieData = null;
const modalInstance = new bootstrap.Modal(document.getElementById('movieModal'));

document.addEventListener("DOMContentLoaded", () => {
    cargarGridDesdeAPI();
    document.getElementById('btnProcesar').addEventListener('click', procesarRecomendacion);
});

async function cargarGridDesdeAPI() {
    const showsIniciales = ["Re:ZERO", "Breaking Bad", "Mr. Robot", "Silicon Valley", "Peaky Blinders", "Dark"];
    const gridContainer = document.getElementById('gridContainer');
    gridContainer.innerHTML = '';

    for (let show of showsIniciales) {
        try {
            const res = await fetch(`https://api.tvmaze.com/search/shows?q=${show}`);
            const data = await res.json();
            
            if (data.length > 0) {
                const info = data[0].show;
                const col = document.createElement('div');
                col.className = 'col-6 col-md-4 col-lg-2';
                col.innerHTML = `
                    <div class="card bg-black border-secondary overflow-hidden cursor-pointer h-100" onclick="abrirDetallesExterna('${info.id}')">
                        <img src="${info.image ? info.image.medium : ''}" class="grid-poster" alt="${info.name}">
                    </div>
                `;
                gridContainer.appendChild(col);
            }
        } catch (e) {
            console.error(e);
        }
    }
}

async function procesarRecomendacion() {
    const genre = document.getElementById('userGenre').value;
    const mood = document.getElementById('userMood').value;
    const time = document.getElementById('userTime').value;
    const consoleDiv = document.getElementById('javaConsole');
    
    consoleDiv.innerHTML += `> [HTTP GET] Contactando a Java Backend en localhost:8080...<br>`;
    consoleDiv.scrollTop = consoleDiv.scrollHeight;
    
    try {
        const javaRes = await fetch(`http://localhost:8080/api/recomendacion?genero=${genre}&animo=${mood}&tiempo=${time}`);
        const javaData = await javaRes.json();
        
        consoleDiv.innerHTML += `> [SUCCESS] Respuesta de IA: ${javaData.titulo}<br>`;
        consoleDiv.scrollTop = consoleDiv.scrollHeight;

        const tvmazeRes = await fetch(`https://api.tvmaze.com/search/shows?q=${javaData.titulo}`);
        const tvmazeData = await tvmazeRes.json();

        if (tvmazeData.length > 0) {
            currentMovieData = tvmazeData[0].show;
            
            document.getElementById('mainImage').src = currentMovieData.image.original || currentMovieData.image.medium;
            document.getElementById('mainTitle').innerText = currentMovieData.name;
            document.getElementById('mainMetadata').innerText = `${genre} • ${time} • ${mood}`;
            document.getElementById('badgeMatch').classList.remove('d-none');
            
            consoleDiv.innerHTML += `> [SUCCESS] Metadata descargada.<br>`;
        }
        consoleDiv.scrollTop = consoleDiv.scrollHeight;
    } catch (error) {
        consoleDiv.innerHTML += `> [ERROR] Servidor Java no responde.<br>`;
    }
}

function abrirDetallesModal() {
    if (!currentMovieData) return;
    
    document.getElementById('modalTitle').innerText = currentMovieData.name;
    document.getElementById('modalImage').src = currentMovieData.image.original || currentMovieData.image.medium;
    document.getElementById('modalScore').innerText = currentMovieData.rating.average ? `Rating: ${currentMovieData.rating.average}` : 'Rating: N/A';
    document.getElementById('modalLanguage').innerText = currentMovieData.language || 'N/A';
    document.getElementById('modalPremiered').innerText = currentMovieData.premiered ? currentMovieData.premiered.substring(0,4) : 'N/A';
    document.getElementById('modalSummary').innerHTML = currentMovieData.summary || 'Sin descripción disponible.';
    
    modalInstance.show();
}

async function abrirDetallesExterna(showId) {
    try {
        const res = await fetch(`https://api.tvmaze.com/shows/${showId}`);
        const data = await res.json();
        currentMovieData = data;
        abrirDetallesModal();
    } catch (e) {
        console.error(e);
    }
}