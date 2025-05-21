async function login(event) {
    event.preventDefault(); 
    let nome = document.getElementById('login-nome').value.trim();
    let senha = document.getElementById('login-senha').value.trim();
    console.log(nome);
    console.log(senha);
    let res = await fetch("http://localhost:8080/auth/login",{
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: nome,
            password: senha,
        })
    })
    let data = await res.json();
    console.log("Resposta do servidor:", data);
    localStorage.setItem('jwt_token', data.token);
    if(res.ok){
        window.location.href = "index.html";
    }
}
async function register(event) {
    event.preventDefault(); 
    let nome = document.getElementById('register-nome').value.trim();
    let senha = document.getElementById('register-senha').value.trim();
    console.log(nome);
    console.log(senha);
    let res = await fetch("http://localhost:8080/usuarios/",{
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            nome: nome,
            senha: senha,
            role: "ROLE_USER"
        })
    })
    let data = await res.json();
    console.log("Resposta do servidor:", data);
}
async function logout(){
    const token = localStorage.getItem('jwt_token');
    await fetch("http://localhost:8080/auth/logout",{
        method: "POST",
        headers:{
            'Authorization': `Bearer ${token}`
        }
    })
    localStorage.removeItem('jwt_token');
}