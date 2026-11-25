fetch('/html/cabecalho.html')
    .then(response => response.text())
    .then(data => {
        document.getElementById('header-placeholder').innerHTML = data;
    });
function mostrarSenha(){
    var inputPass = document.getElementById('password');
    var btnShowPass = document.getElementById('btn-password')

    if(inputPass.type === 'password'){
        inputPass.setAttribute('type', 'text')
        btnShowPass.classList.replace('fa-eye', 'fa-eye-slash')
    } else{
        inputPass.setAttribute('type', 'password')
        btnShowPass.classList.replace('fa-eye-slash', 'fa-eye')
    }
}



 
        
document.addEventListener('DOMContentLoaded', function () {
    const tipo = document.getElementById('tipoPessoa');
    if (!tipo) return; 

    const selects = document.querySelectorAll('.sel-pessoa');

    function atualizar() {
        selects.forEach(function (s) {
            const ativo = s.dataset.tipo === tipo.value;
            s.style.display = ativo ? '' : 'none';
            s.disabled = !ativo;                 
            s.required = ativo && tipo.required;  
            if (!ativo) s.value = '';
        });
    }

    tipo.addEventListener('change', atualizar);
    atualizar();
});