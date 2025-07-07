<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Emprunter un livre</title>
    <style>
        .autocomplete-suggestions {
            border: 1px solid #ccc;
            background: #fff;
            position: absolute;
            max-height: 200px;
            overflow-y: auto;
            z-index: 1000;
            width: 100%;
        }

        .autocomplete-suggestion {
            padding: 8px;
            cursor: pointer;
        }

        .autocomplete-suggestion:hover {
            background: #f0f0f0;
        }
    </style>
</head>
<body>
<h2>Emprunter un livre</h2>

<c:if test="${adherentPenalise}">
    <div style="color:red; background-color: #ffe6e6; padding: 10px; border: 1px solid #ff9999; border-radius: 5px; margin-bottom: 15px;">
        <strong>⚠️ Emprunt impossible</strong><br/>
        ${penaliteInfo}
    </div>
</c:if>

<div style="position:relative;">
    <form action="${pageContext.request.contextPath}/prets/emprunter" method="post" autocomplete="off">
        <label for="livre">Livre :</label>
        <input type="text" name="livreNom" id="livre" required class="form-control"
               placeholder="Commencez à taper le nom du livre..."
               <c:if test="${adherentPenalise}">disabled style="background-color: #f5f5f5; cursor: not-allowed;"</c:if>>
        <input type="hidden" name="livreId" id="livreId"/>
        <div id="autocomplete-list" class="autocomplete-suggestions"></div>
        <input type="hidden" name="adherentId" value="${adherentId}"/>
        <br/>
        <label for="typePret">Type d'emprunt :</label>
        <select name="typePret" id="typePret" required 
                <c:if test="${adherentPenalise}">disabled style="background-color: #f5f5f5; cursor: not-allowed;"</c:if>>
            <option value="emporte">À emporter</option>
            <option value="surplace">Sur place</option>
        </select>
        <br/>
        <input type="submit" value="Emprunter" 
               <c:if test="${adherentPenalise}">disabled style="background-color: #cccccc; cursor: not-allowed; color: #666666;" title="Emprunt impossible : vous êtes actuellement pénalisé"</c:if>/>
        <button type="button" onclick="window.location.href='${pageContext.request.contextPath}/frontoffice'" style="margin-left:10px;">Retour accueil</button>
    </form>
</div>
<script>
    const livres = [
        <c:forEach var="livre" items="${livres}">
        {id: "${livre.id}", titre: "${livre.titre}", auteur: "${livre.auteur}"},
        </c:forEach>
    ];
    const input = document.getElementById('livre');
    const hiddenId = document.getElementById('livreId');
    const suggestionBox = document.getElementById('autocomplete-list');

    // Désactiver l'autocomplete si l'adhérent est pénalisé
    <c:choose>
        <c:when test="${not adherentPenalise}">
            input.addEventListener('input', function () {
                const val = this.value.toLowerCase();
                suggestionBox.innerHTML = '';
                if (!val) return;
                const matches = livres.filter(l => l.titre.toLowerCase().includes(val));
                matches.forEach(livre => {
                    const div = document.createElement('div');
                    div.className = 'autocomplete-suggestion';
                    div.textContent = livre.titre + ' - ' + livre.auteur;
                    div.onclick = function () {
                        input.value = livre.titre;
                        hiddenId.value = livre.id;
                        suggestionBox.innerHTML = '';
                    };
                    suggestionBox.appendChild(div);
                });
            });

            // Si l'utilisateur modifie le champ, on vide l'id caché
            input.addEventListener('change', function () {
                if (!livres.some(l => l.titre === input.value)) {
                    hiddenId.value = '';
                }
            });

            // Fermer la suggestion si on clique ailleurs
            document.addEventListener('click', function (e) {
                if (!suggestionBox.contains(e.target) && e.target !== input) {
                    suggestionBox.innerHTML = '';
                }
            });

            const form = document.querySelector('form');
            form.addEventListener('submit', function (e) {
                if (!hiddenId.value) {
                    e.preventDefault();
                    alert('Veuillez sélectionner un livre dans la liste de suggestions.');
                    input.focus();
                }
            });
        </c:when>
        <c:otherwise>
            // Empêcher la soumission du formulaire si l'adhérent est pénalisé
            const form = document.querySelector('form');
            form.addEventListener('submit', function (e) {
                e.preventDefault();
                alert('Vous ne pouvez pas emprunter de livre car vous êtes actuellement sous pénalité.');
            });
        </c:otherwise>
    </c:choose>
</script>
<c:if test="${not empty erreur}">
    <div style="color:red;">${erreur}</div>
</c:if>
<c:if test="${not empty message}">
    <div style="color:green;">${message}</div>
</c:if>
</body>
</html>
