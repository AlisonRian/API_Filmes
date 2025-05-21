document.addEventListener("DOMContentLoaded", carregarFilmes);
let condicao = true;
function montarTbl(filmes){
    const tabela = document.getElementById("produtosTable");
    tabela.innerHTML = "";
    listafilmes = filmes.content || [];
    listafilmes.forEach(filme => {
        tabela.innerHTML += `
            <tr>
                <td>
                    <img src=${filme.imagemUri} 
                    style="width: 100px; height: 100px;object-fit: contain; background-color:#1C1C1C;" >
                </td>
                <td>${filme.nome}</td>
                <td>${filme.anoLancamento}</td>
                <td>${filme.classificacao}</td>
                <td>${filme.sinopse}</td>
                <td>${filme.genero}</td>
                <td style="text-align: center">
                    <button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#exampleModal" onclick="editarFilmes(${filme.id})" title="Editar">
                        <i class="bi bi-pencil-fill"></i>
                    </button>
                </td>
                <td style="text-align: center">
                    <button class="btn btn-danger btn-sm" onclick="deletarFilmes(${filme.id})" title="Excluir">
                        <i class="bi bi-trash-fill"></i>
                    </button>
                </td>
            </tr>
        `;
    });
}
function editModal(filme){
    const gerarModal =  `
    <div class="modal-header">
    <h1 class="modal-title fs-5" id="exampleModalLabel" style="color: #1C1C1C;" >Editar</h1>
    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
    </div>
    <div class="modal-body" id="modal-body">
        <input type="text" id="nomeEdit" class="form-control mb-2" placeholder="Nome" value="${filme.nome}">
        <span class="erro" id="erro-nome"></span>
        <input type="number" id="anoLancamentoEdit" class="form-control mb-2" placeholder="Ano de lançamento" value=${filme.anoLancamento}>
        <span class="erro" id="erro-anoLancamento"></span>
        <textarea id='sinopseEdit' class="form-control mb-2" placeholder="Sinopse" >${filme.sinopse}</textarea>
        <span class="erro" id="erro-sinopse"></span>
    
        <div class="input-group mb-3">
            <label class="input-group-text" for="genero">Gênero</label>
            <select class="form-select" id="generoEdit">
                <option value="Ação">Ação</option>
                <option value="Aventura">Aventura</option>
                <option value="Comédia">Comédia</option>
                <option value="Drama">Drama</option>
                <option value="Terror">Terror</option>
                <option value="Ficção Científica">Ficção Científica (Sci-Fi)</option>
                <option value="Fantasia">Fantasia</option>
                <option value="Romance">Romance</option>
                <option value="Mistério">Mistério</option>
                <option value="Suspense">Suspense</option>
                <option value="Documentário">Documentário</option>
                <option value="Animação">Animação</option>
                <option value="Musical">Musical</option>
                <option value="Histórico">Histórico</option>
            </select>
        </div>
    
        <div class="input-group mb-3">
            <label class="input-group-text" for="classificacao">Classificação</label>
            <select class="form-select" id="classificacaoEdit">
                <option value="Livre">Livre</option>
                <option value="10 anos">10 anos</option>
                <option value="12 anos">12 anos</option>
                <option value="14 anos">14 anos</option>
                <option value="16 anos">16 anos</option>
                <option value="18 anos">18 anos</option>
            </select>
        </div>

        <input type="file" id="imagemUriEdit" class="form-control mb-2" placeholder="Capa" value="${filme.caminho}">
        <span class="erro" id="erro-imagemUri"></span>
    </div>
    <div class="modal-footer">
    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
    <button type="button" class="btn btn-primary" onclick="editSaveFilmes(${filme.id})">Salvar</button>
    </div>`;
    document.getElementById('modal-content').innerHTML = gerarModal;
}
function saveModal(){
    const gerarModal = `
    <div class="modal-header">
        <h1 class="modal-title fs-5" id="exampleModalLabel" style="color: #1C1C1C;">Adicionar</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
    </div>

    <div class="modal-body" id="modal-body">
        <input type="text" id="nome" class="form-control mb-2" placeholder="Nome">
        <span class="erro" id="erro-nome"></span>
        <input type="number" id="anoLancamento" class="form-control mb-2" placeholder="Ano de lançamento">
        <span class="erro" id="erro-anoLancamento"></span>
        <textarea id='sinopse' class="form-control mb-2" placeholder="Sinopse"></textarea>
        <span class="erro" id="erro-sinopse"></span>
        <div class="input-group mb-3">
            <label class="input-group-text" for="genero">Gênero</label>
            <select class="form-select" id="genero">
                <option selected value="Ação">Ação</option>
                <option value="Aventura">Aventura</option>
                <option value="Comédia">Comédia</option>
                <option value="Drama">Drama</option>
                <option value="Terror">Terror</option>
                <option value="Ficção Científica">Ficção Científica (Sci-Fi)</option>
                <option value="Fantasia">Fantasia</option>
                <option value="Romance">Romance</option>
                <option value="Mistério">Mistério</option>
                <option value="Suspense">Suspense</option>
                <option value="Documentário">Documentário</option>
                <option value="Animação">Animação</option>
                <option value="Musical">Musical</option>
                <option value="Histórico">Histórico</option>
            </select>
        </div>

        <div class="input-group mb-3">
            <label class="input-group-text" for="classificacao">Classificação</label>
            <select class="form-select" id="classificacao">
                <option selected value="Livre">Livre</option>
                <option value="10 anos">10 anos</option>
                <option value="12 anos">12 anos</option>
                <option value="14 anos">14 anos</option>
                <option value="16 anos">16 anos</option>
                <option value="18 anos">18 anos</option>
            </select>
        </div>

        
        <input type="file" id="imagemUri" class="form-control mb-2" placeholder="Capa">
        <span class="erro" id="erro-imagemUri"></span>
    </div>

    <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
        <button type="button" class="btn btn-primary" onclick="salvarFilmes()">Salvar</button>
    </div>`;
    document.getElementById('modal-content').innerHTML = gerarModal;
    let myModal = new bootstrap.Modal(document.getElementById('exampleModal'));
    myModal.show();
}
async function carregarFilmes() {
    const response = await fetch("http://localhost:8080/filmes/");
    const filmes = await response.json();
    montarTbl(filmes);
    if (!condicao) {
        document.getElementById("reset-filtros").style.display = "none";
        condicao=true;
    }
}
async function carregarUsuarios() {
    const response = await fetch("http://localhost:8080/filmes/");
    const filmes = await response.json();
    console.log(filmes);
}
async function pesquisarNome(){
    let url = "http://localhost:8080/filmes/filtrar?";
    let nome = document.getElementById("filtrarNome").value;
    url+= "nome="+encodeURIComponent(nome);
    const response = await fetch(url);
    const filmes = await response.json();
    montarTbl(filmes);
    if (condicao) {
        document.getElementById("reset-filtros").style.display = "block";
        condicao=false;
    }
}
async function pesquisar() {
    let url = "http://localhost:8080/filmes/filtrar?";
    let nome = document.getElementById("filtrarNome").value;
    if (nome && nome.trim() !== "") {
        url += "nome=" + encodeURIComponent(nome) + "&";
    }
    let ano = document.getElementById("filtrarAno").value;
    if (ano && ano.trim() !== "") {
        url += "anoLancamento=" + encodeURIComponent(ano) + "&";
    }

    let genero = document.getElementById("filtrarGenero").value;
    if (genero && genero.trim() !== "") {
        url += "genero=" + encodeURIComponent(genero) + "&";
    }

    if (url.endsWith("&")) {
        url = url.slice(0, -1);
    }

    const response = await fetch(url);
    const filmes = await response.json();
    montarTbl(filmes);
    if (condicao) {
        document.getElementById("reset-filtros").style.display = "block";
        condicao=false;
    }
    var modal = bootstrap.Modal.getInstance(document.getElementById('exampleModal'));
    modal.hide();
}
async function filtros() {
    const gerarModal =  `
    <div class="modal-header">
    <h1 class="modal-title fs-5" id="exampleModalLabel" style="color: #1C1C1C;">Filtros</h1>
    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
    </div>
    <div class="modal-body" id="modal-body">
        <input type="number" id="filtrarAno" class="form-control mb-2" placeholder="Ano de lançamento">
        <div class="input-group mb-3">
            <label class="input-group-text" for="genero">Gênero</label>
            <select class="form-select" id="filtrarGenero">
                <option value="">Selecione um gênero</option>
                <option value="Ação">Ação</option>
                <option value="Aventura">Aventura</option>
                <option value="Comédia">Comédia</option>
                <option value="Drama">Drama</option>
                <option value="Terror">Terror</option>
                <option value="Ficção Científica">Ficção Científica (Sci-Fi)</option>
                <option value="Fantasia">Fantasia</option>
                <option value="Romance">Romance</option>
                <option value="Mistério">Mistério</option>
                <option value="Suspense">Suspense</option>
                <option value="Documentário">Documentário</option>
                <option value="Animação">Animação</option>
                <option value="Musical">Musical</option>
                <option value="Histórico">Histórico</option>
            </select>
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
        <button type="button" class="btn btn-primary" onclick="pesquisar()">Buscar</button>
    </div>`;
    document.getElementById('modal-content').innerHTML = gerarModal;
    let myModal = new bootstrap.Modal(document.getElementById('exampleModal'));
    myModal.show();
    carregarFilmes();
}
async function deletarFilmes(id) {
    await fetch("http://localhost:8080/filmes/"+id, {method: "DELETE"});
    carregarFilmes();
}

async function editarFilmes(id) {
    const response = await fetch("http://localhost:8080/filmes/"+id);
    const filme = await response.json();
    editModal(filme);
    carregarFilmes();
}
async function salvarFilmes(){
    let nome = document.getElementById("nome").value;
    let imagemUri = document.getElementById("imagemUri");
    let sinopse = document.getElementById("sinopse").value;
    let anoLancamento = document.getElementById("anoLancamento").value;
    let genero = document.getElementById("genero").value;
    let classificacao = document.getElementById("classificacao").value;
    const token = localStorage.getItem('jwt_token');
    console.log(token);
    let formData = new FormData();
    formData.append("file", imagemUri.files[0]);

    let response = await fetch("http://localhost:8080/images/upload", {
        method: "POST",
        body: formData,
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });

    caminho = await response.text();

    let res = await fetch("http://localhost:8080/filmes/", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({
            nome: nome,
            anoLancamento: parseInt(anoLancamento),
            classificacao: classificacao,
            sinopse: sinopse,
            genero:  genero,
            imagemUri: caminho
        })
    })
    document.querySelectorAll(".erro").forEach(e => e.textContent = "");
    if(res.status != 201){
        let erros = await res.json();
        console.log(erros);
        for(let campo in erros){
            let spanErro = document.getElementById(`erro-${campo}`);
                if (spanErro) {
                    spanErro.textContent = erros[campo]; // Exibir erro no campo correspondente
                    spanErro.style.color = "red";
                }
        }
    }else{
        carregarFilmes();
        var modal = bootstrap.Modal.getInstance(document.getElementById('exampleModal'));
        modal.hide();
    }
}
async function editSaveFilmes(id){
    let nome = document.getElementById("nomeEdit").value;
    let imagemUri = document.getElementById("imagemUriEdit");
    let sinopse = document.getElementById("sinopseEdit").value;
    let anoLancamento = document.getElementById("anoLancamentoEdit").value;
    let genero = document.getElementById("generoEdit").value;
    let classificacao = document.getElementById("classificacaoEdit").value;
    
    
    let formData = new FormData();
    formData.append("file", imagemUri.files[0]);

    let response = await fetch("http://localhost:8080/images/upload", {
        method: "POST",
        body: formData
    });

    caminho = await response.text();

    let res = await fetch("http://localhost:8080/filmes/"+id, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            nome: nome,
            anoLancamento: parseInt(anoLancamento),
            classificacao: classificacao,
            sinopse: sinopse,
            genero:  genero,
            imagemUri: caminho
        })
    })
    console.log("Status:", res.status);
    console.log("Content-Type:", res.headers.get("Content-Type"));
    document.querySelectorAll(".erro").forEach(e => e.textContent = "");
    if(!res.ok){
        let erros = await res.json();
        console.log(erros);
        for(let campo in erros){
            let spanErro = document.getElementById(`erro-${campo}`);
                if (spanErro) {
                    spanErro.textContent = erros[campo]; // Exibir erro no campo correspondente
                    spanErro.style.color = "red";
                }
        }
    }else{
        carregarFilmes();
        var modal = bootstrap.Modal.getInstance(document.getElementById('exampleModal'));
        modal.hide();
    }
}
