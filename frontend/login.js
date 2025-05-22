const apiUrl = "http://localhost:8080";
async function login(event) {
    sessionStorage.setItem('logado','false');
    event.preventDefault(); 
    let nome = document.getElementById('login-nome').value.trim();
    let senha = document.getElementById('login-senha').value.trim();
    let res = await fetch(apiUrl+"/auth/login",{
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
        sessionStorage.setItem('logado','true');
        let role = await fetch(apiUrl+"/auth/"+data.token,{
        method: "GET",
        });
        let data_role =  await role.text();
        sessionStorage.setItem('role',data_role);
        setTimeout(() => {
            window.location.href = "index.html";
        }, 500); 
    }else{
        // alert("Falhou.");
        console.log("Resposta do servidor:", data);
        document.querySelectorAll(".erro").forEach(e => e.textContent = "");
        for(let campo in data){
            let span = document.getElementById(`erro-${campo}`);
            if(span){
                span.textContent = data[campo];
                span.style.color = "red";
            }
        }
    }
}
async function register(event) {
    event.preventDefault(); 
    let nome = document.getElementById('register-nome').value.trim();
    let senha = document.getElementById('register-senha').value.trim();
    console.log(nome);
    console.log(senha);
    let res = await fetch(apiUrl+"/usuarios/",{
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
    document.querySelectorAll(".erro").forEach(e => e.textContent = "");
    if(res.status!=201){
        for(let campo in data){
            let span = document.getElementById(`erro-${campo}`);
            if(span){
                span.textContent = data[campo];
                span.style.color = "red";
            }
        }
    }else{
        alert("Sua conta foi criada com sucesso!");
        window.location.href = "login_page.html";
        
    }
    
}
async function logout(){
    const token = localStorage.getItem('jwt_token');
    await fetch(apiUrl+"/auth/logout",{
        method: "POST",
        headers:{
            'Authorization': `Bearer ${token}`
        }
    })
    sessionStorage.setItem('logado','false');
    sessionStorage.removeItem('role');
    localStorage.removeItem('jwt_token');
    location.reload();
}
async function admin() {
    sessionStorage.setItem('logado','false');
    let res = await fetch(apiUrl+"/auth/login",{
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: "Admin",
            password: "Admin@10",
        })
    })
    let data = await res.json();
    localStorage.setItem('jwt_token', data.token);
    if(res.ok){
        sessionStorage.setItem('logado','true');
        let role = await fetch(apiUrl+"/auth/"+data.token,{
        method: "GET",
        });
        let data_role =  await role.text();
        sessionStorage.setItem('role',data_role);
        setTimeout(() => {
            window.location.href = "index.html";
        }, 500); 
    }else{
        document.querySelectorAll(".erro").forEach(e => e.textContent = "");
        for(let campo in data){
            let span = document.getElementById(`erro-${campo}`);
            if(span){
                span.textContent = data[campo];
                span.style.color = "red";
            }
        }
    }
}
async function user() {
    sessionStorage.setItem('logado','false');
    let res = await fetch(apiUrl+"/auth/login",{
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: "User",
            password: "User@123",
        })
    })
    let data = await res.json();
    localStorage.setItem('jwt_token', data.token);
    if(res.ok){
        sessionStorage.setItem('logado','true');
        let role = await fetch(apiUrl+"/auth/"+data.token,{
        method: "GET",
        });
        let data_role =  await role.text();
        sessionStorage.setItem('role',data_role);
        setTimeout(() => {
            window.location.href = "index.html";
        }, 500); 
    }else{
        document.querySelectorAll(".erro").forEach(e => e.textContent = "");
        for(let campo in data){
            let span = document.getElementById(`erro-${campo}`);
            if(span){
                span.textContent = data[campo];
                span.style.color = "red";
            }
        }
    }
}