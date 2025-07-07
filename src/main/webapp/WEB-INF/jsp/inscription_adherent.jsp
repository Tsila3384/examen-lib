<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Inscription Adhérent - Bibliothèque</title>
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
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 1rem;
        }

        .auth-container {
            background: white;
            border-radius: var(--border-radius);
            box-shadow: var(--box-shadow);
            padding: 2.5rem;
            width: 100%;
            max-width: 500px;
            animation: fadeIn 0.5s ease;
        }

        .auth-title {
            color: var(--secondary);
            text-align: center;
            margin-bottom: 1.5rem;
            font-size: 1.8rem;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 0.5rem;
        }

        .auth-form {
            display: flex;
            flex-direction: column;
            gap: 1.2rem;
        }

        .form-group {
            display: flex;
            flex-direction: column;
            gap: 0.5rem;
        }

        .form-group label {
            font-weight: 500;
            color: var(--secondary);
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .form-group input,
        .form-group select {
            padding: 0.8rem 1rem;
            border: 1px solid #ddd;
            border-radius: var(--border-radius);
            font-family: 'Poppins', sans-serif;
            font-size: 1rem;
            transition: var(--transition);
        }

        .form-group input:focus,
        .form-group select:focus {
            outline: none;
            border-color: var(--accent);
            box-shadow: 0 0 0 3px rgba(72, 149, 239, 0.2);
        }

        .toggle-fields {
            margin-bottom: 1rem;
        }

        .form-check {
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .form-check input {
            width: 18px;
            height: 18px;
            accent-color: var(--primary);
        }

        .form-check label {
            font-size: 0.95rem;
            color: var(--dark);
            cursor: pointer;
        }

        .visible-fields {
            display: flex;
            flex-direction: column;
            gap: 1.2rem;
            max-height: 1000px;
            overflow: hidden;
            transition: all 0.5s ease;
        }

        .hidden-fields {
            display: flex;
            flex-direction: column;
            gap: 1.2rem;
            max-height: 0;
            overflow: hidden;
            transition: all 0.5s ease;
        }

        .form-actions {
            display: flex;
            gap: 1rem;
            margin-top: 1rem;
        }

        .btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            padding: 0.8rem 1.5rem;
            border-radius: 50px;
            font-weight: 500;
            text-decoration: none;
            transition: var(--transition);
            border: none;
            cursor: pointer;
            font-size: 0.95rem;
            gap: 0.5rem;
            flex: 1;
        }

        .btn-primary {
            background: var(--primary);
            color: white;
        }

        .btn-primary:hover {
            background: var(--secondary);
            transform: translateY(-2px);
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

        .alert {
            padding: 1rem;
            border-radius: var(--border-radius);
            margin-top: 1.5rem;
            font-weight: 500;
            text-align: center;
        }

        .alert-info {
            background-color: #e2f0fd;
            color: #1a56db;
            border: 1px solid #c3ddfd;
        }

        select {
            appearance: none;
            background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='16' height='16' fill='%234361ee' viewBox='0 0 16 16'%3E%3Cpath d='M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z'/%3E%3C/svg%3E");
            background-repeat: no-repeat;
            background-position: right 1rem center;
            background-size: 16px 12px;
            padding-right: 2.5rem;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        @media (max-width: 576px) {
            .auth-container {
                padding: 1.5rem;
            }
            
            .form-actions {
                flex-direction: column;
            }
        }
    </style>
</head>
<body>
    <div class="auth-container">
        <h2 class="auth-title"><span>📝</span> Inscription Adhérent</h2>
        
        <form method="post" action="/inscription-adherent" class="auth-form">
            <div class="toggle-fields">
                <div class="form-check">
                    <input type="checkbox" id="dejaPersonne" name="dejaPersonne" onclick="toggleFields()">
                    <label for="dejaPersonne">Je suis déjà une personne enregistrée dans le système</label>
                </div>
            </div>
            
            <div class="form-group">
                <label for="email"><span>📧</span> Adresse Email</label>
                <input type="email" id="email" name="email" required placeholder="votre.email@exemple.com">
            </div>
            
            <div id="fieldsToHide" class="visible-fields">
                <div class="form-group">
                    <label for="nom"><span>👤</span> Nom de famille</label>
                    <input type="text" id="nom" name="nom" required placeholder="Votre nom">
                </div>
                <div class="form-group">
                    <label for="prenom"><span>👤</span> Prénom</label>
                    <input type="text" id="prenom" name="prenom" required placeholder="Votre prénom">
                </div>
                <div class="form-group">
                    <label for="dateNaissance"><span>📅</span> Date de naissance</label>
                    <input type="date" id="dateNaissance" name="dateNaissance" required>
                </div>
                <div class="form-group">
                    <label for="adresse"><span>🏠</span> Adresse complète</label>
                    <input type="text" id="adresse" name="adresse" required placeholder="Votre adresse complète">
                </div>
                <div class="form-group">
                    <label for="typePersonneId"><span>👥</span> Type de personne</label>
                    <select id="typePersonneId" name="typePersonneId" required>
                        <option value="">Sélectionnez un type</option>
                        <c:forEach var="type" items="${typesPersonne}">
                            <option value="${type.id}">${type.libelle}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            
            <div class="form-group">
                <label for="motDePasse"><span>🔐</span> Mot de passe</label>
                <input type="password" id="motDePasse" name="motDePasse" required placeholder="Choisissez un mot de passe sécurisé">
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">
                    <span>✅</span> S'inscrire
                </button>
                <button type="button" class="btn btn-secondary" onclick="window.location.href='${pageContext.request.contextPath}/login'">
                    <span>❌</span> Retour connexion
                </button>
            </div>
        </form>
        
        <c:if test="${not empty message}">
            <div class="alert alert-info">${message}</div>
        </c:if>
    </div>

    <script>
        function toggleFields() {
            const check = document.getElementById('dejaPersonne').checked;
            const fieldsContainer = document.getElementById('fieldsToHide');
            
            if (check) {
                fieldsContainer.className = 'hidden-fields';
            } else {
                fieldsContainer.className = 'visible-fields';
            }
            
            // Gérer les champs requis
            document.getElementById('nom').required = !check;
            document.getElementById('dateNaissance').required = !check;
            document.getElementById('prenom').required = !check;
            document.getElementById('adresse').required = !check;
            document.getElementById('typePersonneId').required = !check;
        }

        window.onload = function () {
            toggleFields();
        };
    </script>
</body>
</html>