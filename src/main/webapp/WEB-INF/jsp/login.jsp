<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Connexion - Bibliothèque</title>
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
            max-width: 450px;
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
            gap: 1.5rem;
        }

        .form-group {
            display: flex;
            flex-direction: column;
            gap: 0.5rem;
            position: relative;
        }

        .form-group label {
            font-weight: 500;
            color: var(--secondary);
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .form-group input {
            padding: 0.8rem 1rem;
            border: 1px solid #ddd;
            border-radius: var(--border-radius);
            font-family: 'Poppins', sans-serif;
            font-size: 1rem;
            transition: var(--transition);
        }

        .form-group input:focus {
            outline: none;
            border-color: var(--accent);
            box-shadow: 0 0 0 3px rgba(72, 149, 239, 0.2);
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
            font-size: 1rem;
            gap: 0.5rem;
            width: 100%;
        }

        .btn-primary {
            background: var(--primary);
            color: white;
        }

        .btn-primary:hover {
            background: var(--secondary);
            transform: translateY(-2px);
        }

        .auth-links {
            margin-top: 1.5rem;
            text-align: center;
            display: flex;
            flex-direction: column;
            gap: 0.8rem;
        }

        .auth-links a {
            color: var(--primary);
            text-decoration: none;
            font-weight: 500;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 0.5rem;
            transition: var(--transition);
        }

        .auth-links a:hover {
            color: var(--secondary);
            transform: translateX(5px);
        }

        .alert {
            padding: 1rem;
            border-radius: var(--border-radius);
            margin-top: 1.5rem;
            font-weight: 500;
            text-align: center;
        }

        .alert-danger {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }

        .password-toggle {
            position: absolute;
            right: 1rem;
            top: 2.2rem;
            cursor: pointer;
            color: #6c757d;
            background: none;
            border: none;
            font-size: 1.2rem;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        @media (max-width: 576px) {
            .auth-container {
                padding: 1.5rem;
            }
        }
    </style>
</head>
<body>
<div class="auth-container">
    <h2 class="auth-title"><span>📚</span> Connexion Bibliothèque</h2>
    <form method="post" action="/login" autocomplete="off" class="auth-form">
        <div class="form-group">
            <label for="email"><span>📧</span> Adresse Email</label>
            <input list="emails" id="email" name="email" required autocomplete="off" placeholder="Entrez votre email">
            <datalist id="emails">
                <c:forEach var="adherent" items="${adherents}">
                    <option value="${adherent.email}">${adherent.email} (Adhérent)</option>
                </c:forEach>
                <c:forEach var="bibliothecaire" items="${bibliothecaires}">
                    <option value="${bibliothecaire.email}">${bibliothecaire.email} (Bibliothécaire)</option>
                </c:forEach>
            </datalist>
        </div>
        
        <div class="form-group">
            <label for="motDePasse"><span>🔐</span> Mot de passe</label>
            <input type="password" id="motDePasse" name="motDePasse" required autocomplete="off" placeholder="Entrez votre mot de passe">
            <button type="button" class="password-toggle" onclick="togglePassword()">👁️</button>
        </div>
        
        <button type="submit" class="btn btn-primary">
            <span>🚪</span> Se connecter
        </button>
    </form>
    
    <div class="auth-links">
        <a href="/inscription-adherent">
            <span>📝</span> Créer un compte adhérent
        </a>
        <a href="/" style="color: var(--accent); font-size: 0.9rem;">
            <span>🏠</span> Retour à l'accueil
        </a>
    </div>
    
    <c:if test="${not empty message}">
        <div class="alert alert-danger">${message}</div>
    </c:if>
</div>

<script>
    function togglePassword() {
        const passwordInput = document.getElementById('motDePasse');
        const toggleBtn = document.querySelector('.password-toggle');
        
        if (passwordInput.type === 'password') {
            passwordInput.type = 'text';
            toggleBtn.textContent = '🙈';
        } else {
            passwordInput.type = 'password';
            toggleBtn.textContent = '👁️';
        }
    }

    // Remplissage sécurisé via une requête AJAX si nécessaire
    document.getElementById('email').addEventListener('change', function() {
        // Ici vous pourriez implémenter une vérification AJAX sécurisée
        // plutôt que d'exposer les mots de passe dans le code source
    });
</script>
</body>
</html>