document.addEventListener("DOMContentLoaded", function() {
    const usuario = JSON.parse(sessionStorage.getItem('usuario')) || {};
    const usuarioId = usuario.id;

    fetch(`/usuarios/${usuarioId}`)
        .then(response => {
            if (!response.ok) throw new Error('Erro ao carregar usuário: ' + response.statusText);
            return response.json();
        })
        .then(data => {
            const form = document.getElementById('user-info-form');
            const fields = [
                { label: 'Nome', type: 'text', id: 'nome', value: data.nome },
                { label: 'Email', type: 'email', id: 'email', value: data.email },
                { label: 'Telefone', type: 'text', id: 'telefone', value: data.telefone }
            ];

            fields.forEach(field => {
                const label = document.createElement('label');
                label.setAttribute('for', field.id);
                label.textContent = field.label + ':';

                const input = document.createElement('input');
                input.type = field.type;
                input.id = field.id;
                input.name = field.id;
                input.value = field.value;

                form.appendChild(label);
                form.appendChild(input);
            });
        })
        .catch(error => {
            console.error(error);
            Swal.fire('Erro', error.message, 'error');
        });

    // Alterar senha
    const senhaForm = document.getElementById('senha-form');
    senhaForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const currentPassword = document.getElementById('senha-atual').value;
        const newPassword = document.getElementById('nova-senha').value;



        fetch(`/usuarios/${usuarioId}/senha`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
            },
            body:newPassword
        })
        .then(response => {
            if (!response.ok) throw new Error('Erro ao alterar senha: ' + response.statusText);
            Swal.fire('Sucesso', 'Senha alterada com sucesso!', 'success');
        })
        .catch(error => {
            console.error(error);
            Swal.fire('Erro', error.message, 'error');
        });
    });
});
