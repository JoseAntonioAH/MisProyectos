document.addEventListener('DOMContentLoaded', () => {
    
    // menu en movil
    const botonMenu = document.getElementById('abrirMenu');
    const menuNavegacion = document.getElementById('menuNavegacion');

    botonMenu.addEventListener('click', () => {
        menuNavegacion.classList.toggle('abierto');
        //si se abre el menu, se cambian las lineas por una X
        const icono = botonMenu.querySelector('i');
        if (menuNavegacion.classList.contains('abierto')) {
            icono.classList.remove('fa-bars');
            icono.classList.add('fa-xmark');
        } else {
            icono.classList.remove('fa-xmark');
            icono.classList.add('fa-bars');
        }
    });

    // si se entra en un apartado del menu, se cierra el menu
    const enlacesMenu = document.querySelectorAll('.enlace-menu');
    enlacesMenu.forEach(enlace => {
        enlace.addEventListener('click', () => {
            menuNavegacion.classList.remove('abierto');
            const icono = botonMenu.querySelector('i');
            icono.classList.remove('fa-xmark');
            icono.classList.add('fa-bars');
        });
    });

    // se van activando los elementos al hacer scroll
    const secciones = document.querySelectorAll('section');
    
    window.addEventListener('scroll', () => {
        let seccionActualId = '';
        const posicionScroll = window.scrollY + 120; // cambia la linea al menu correspondiente

        secciones.forEach(seccion => {
            const seccionArriba = seccion.offsetTop;
            const seccionAltura = seccion.offsetHeight;

            if (posicionScroll >= seccionArriba && posicionScroll < seccionArriba + seccionAltura) {
                seccionActualId = seccion.getAttribute('id');
            }
        });

        enlacesMenu.forEach(enlace => {
            enlace.classList.remove('activo');
            if (enlace.getAttribute('href') === `#${seccionActualId}`) {
                enlace.classList.add('activo');
            }
        });
    });

    // formulario
    const formularioContacto = document.getElementById('formularioContacto');

    formularioContacto.addEventListener('submit', (evento) => {
        evento.preventDefault(); // no se recarga la pagina al enviarlo

        // coge los valores del formulario
        const nombre = document.getElementById('nombre').value.trim();
        const correo = document.getElementById('correo').value.trim();
        const mensaje = document.getElementById('mensaje').value.trim();
        const privacidadAceptada = document.getElementById('privacidad').checked;

        if (!nombre || !correo || !mensaje || !privacidadAceptada) {
            alert('Por favor, rellena todos los campos obligatorios y acepta la política de privacidad.');
            return;
        }

        // prueba por consola de que se reocgen los datos
        console.log('Datos recogidos del formulario:', { nombre, correo, mensaje });
        
        // mensaje enviado
        alert('¡Muchas gracias, '+nombre+' ! Tu solicitud de cita ha sido registrada confidencialmente. Nos pondremos en contacto contigo lo antes posible.');
        
        // vaciar formulario
        formularioContacto.reset();
    });
});