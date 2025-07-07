<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Emprunter un livre - Bibliothèque</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap">
    <style>
        :root {
            --primary: #4361ee;
            --secondary: #3f37c9;
            --accent: #4895ef;
            --danger: #f72585;
            --success: #4cc9f0;
            --warning: #f8961e;
            --info: #560bad;
            --light: #f8f9fa;
            --dark: #212529;
            --border-radius: 12px;
            --box-shadow: 0 10px 20px rgba(0,0,0,0.1);
            --transition: all 0.3s ease;
        }

        body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            min-height: 100vh;
            margin: 0;
            padding: 2rem;
            color: var(--dark);
        }

        .dashboard-container {
            max-width: 800px;
            margin: 0 auto;
            background: white;
            border-radius: var(--border-radius);
            box-shadow: var(--box-shadow);
            padding: 2rem;
            animation: fadeIn 0.8s ease;
        }

        .dashboard-header {
            text-align: center;
            margin-bottom: 2rem;
        }

        .dashboard-header h2 {
            font-size: 2rem;
            color: var(--secondary);
            margin-bottom: 0.5rem;
            font-weight: 600;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 0.5rem;
        }

        .form-group {
            margin-bottom: 1.5rem;
        }

        label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: 500;
            color: var(--secondary);
        }

        .form-control {
            width: 100%;
            padding: 0.8rem 1rem;
            border: 1px solid #ddd;
            border-radius: var(--border-radius);
            font-family: 'Poppins', sans-serif;
            font-size: 1rem;
            transition: var(--transition);
        }

        .form-control:focus {
            outline: none;
            border-color: var(--accent);
            box-shadow: 0 0 0 3px rgba(67, 97, 238, 0.2);
        }

        .form-control:disabled {
            background-color: #f5f5f5;
            cursor: not-allowed;
        }

        select.form-control {
            appearance: none;
            background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='16' height='16' fill='%234361ee' viewBox='0 0 16 16'%3E%3Cpath d='M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z'/%3E%3C/svg%3E");
            background-repeat: no-repeat;
            background-position: right 1rem center;
            background-size: 16px 12px;
            padding-right: 2.5rem;
        }

        .btn {
            display: inline-block;
            padding: 0.8rem 1.8rem;
            border-radius: 50px;
            font-weight: 500;
            text-decoration: none;
            transition: var(--transition);
            border: none;
            cursor: pointer;
            font-size: 1rem;
            text-align: center;
        }

        .btn-primary {
            background: var(--primary);
            color: white;
        }

        .btn-primary:hover {
            background: var(--secondary);
            transform: translateY(-2px);
        }

        .btn-primary:disabled {
            background: #cccccc;
            color: #666666;
            cursor: not-allowed;
            transform: none;
        }

        .btn-secondary {
            background: white;
            color: var(--secondary);
            border: 1px solid var(--secondary);
        }

        .btn-secondary:hover {
            background: #f8f9fa;
            transform: translateY(-2px);
        }

        .btn-group {
            display: flex;
            gap: 1rem;
            justify-content: center;
            margin-top: 2rem;
        }

        .alert {
            padding: 1rem;
            border-radius: var(--border-radius);
            margin-bottom: 1.5rem;
            font-weight: 500;
        }

        .alert-warning {
            background-color: #fff3cd;
            color: #856404;
            border: 1px solid #ffeeba;
        }

        .alert-error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }

        .alert-success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }

        .autocomplete-suggestions {
            position: absolute;
            width: 100%;
            max-height: 300px;
            overflow-y: auto;
            background: white;
            border: 1px solid #ddd;
            border-radius: 0 0 var(--border-radius) var(--border-radius);
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            z-index: 1000;
            display: none;
        }

        .autocomplete-suggestion {
            padding: 0.8rem 1rem;
            cursor: pointer;
            transition: var(--transition);
            border-bottom: 1px solid #f0f0f0;
        }

        .autocomplete-suggestion:last-child {
            border-bottom: none;
        }

        .autocomplete-suggestion:hover {
            background-color: #f8f9fa;
        }

        .autocomplete-suggestion strong {
            color: var(--secondary);
        }

        .autocomplete-suggestion small {
            color: #6c757d;
            font-size: 0.85rem;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        @media (max-width: 768px) {
            .btn-group {
                flex-direction: column;
            }
            
            .btn {
                width: 100%;
            }
        }
    </style>
</head>
<body>
    <div class="dashboard-container">
        <div class="dashboard-header">
            <h2><span>📚</span> Emprunter un livre</h2>
        </div>

        <c:if test="${adherentPenalise}">
            <div class="alert alert-warning">
                <strong>⚠️ Emprunt impossible</strong><br/>
                ${penaliteInfo}
            </div>
        </c:if>

        <div style="position:relative;">
            <form action="${pageContext.request.contextPath}/prets/emprunter" method="post" autocomplete="off">
                <div class="form-group">
                    <label for="livre">Rechercher un livre</label>
                    <input type="text" name="livreNom" id="livre" required class="form-control"
                           placeholder="Commencez à taper le titre ou l'auteur..."
                           <c:if test="${adherentPenalise}">disabled</c:if>>
                    <input type="hidden" name="livreId" id="livreId"/>
                    <div id="autocomplete-list" class="autocomplete-suggestions"></div>
                </div>
                
                <input type="hidden" name="adherentId" value="${adherentId}"/>
                
                <div class="form-group">
                    <label for="typePret">Type d'emprunt</label>
                    <select name="typePret" id="typePret" required class="form-control"
                            <c:if test="${adherentPenalise}">disabled</c:if>>
                        <option value="">-- Sélectionnez --</option>
                        <option value="emporte">À emporter (2 semaines)</option>
                        <option value="surplace">Sur place (consultation uniquement)</option>
                    </select>
                </div>
                
                <div class="btn-group">
                    <input type="submit" value="Emprunter" class="btn btn-primary"
                           <c:if test="${adherentPenalise}">disabled title="Emprunt impossible : vous êtes actuellement pénalisé"</c:if>/>
                    <button type="button" class="btn btn-secondary" onclick="window.location.href='${pageContext.request.contextPath}/frontoffice'">
                        Retour à l'accueil
                    </button>
                </div>
            </form>
        </div>
        
        <c:if test="${not empty erreur}">
            <div class="alert alert-error">
                <strong>❌ Erreur</strong><br>
                ${erreur}
            </div>
        </c:if>
        <c:if test="${not empty message}">
            <div class="alert alert-success">
                <strong>✅ Succès</strong><br>
                ${message}
            </div>
        </c:if>
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
                    const query = this.value.toLowerCase();
                    suggestionBox.innerHTML = '';
                    
                    if (query.length > 1) {
                        const filteredLivres = livres.filter(livre => 
                            livre.titre.toLowerCase().includes(query) || 
                            livre.auteur.toLowerCase().includes(query)
                        );
                        
                        if (filteredLivres.length > 0) {
                            suggestionBox.style.display = 'block';
                            filteredLivres.slice(0, 5).forEach(livre => {
                                const suggestion = document.createElement('div');
                                suggestion.className = 'autocomplete-suggestion';
                                suggestion.innerHTML = `<strong>${livre.titre}</strong><br><small>par ${livre.auteur}</small>`;
                                suggestion.onclick = function() {
                                    input.value = livre.titre;
                                    hiddenId.value = livre.id;
                                    suggestionBox.style.display = 'none';
                                };
                                suggestionBox.appendChild(suggestion);
                            });
                        } else {
                            suggestionBox.style.display = 'none';
                        }
                    } else {
                        suggestionBox.style.display = 'none';
                    }
                });

                // Si l'utilisateur modifie le champ, on vide l'id caché
                input.addEventListener('change', function () {
                    const selectedLivre = livres.find(livre => livre.titre === this.value);
                    if (!selectedLivre) {
                        hiddenId.value = '';
                    }
                });

                // Fermer la suggestion si on clique ailleurs
                document.addEventListener('click', function (e) {
                    if (!input.contains(e.target) && !suggestionBox.contains(e.target)) {
                        suggestionBox.style.display = 'none';
                    }
                });

                const form = document.querySelector('form');
                form.addEventListener('submit', function (e) {
                    if (!hiddenId.value) {
                        e.preventDefault();
                        alert('Veuillez sélectionner un livre dans la liste de suggestions.');
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
</body>
</html>