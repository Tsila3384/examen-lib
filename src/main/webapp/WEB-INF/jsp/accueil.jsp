<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Accueil - Bibliothèque</title>
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
            padding: 0;
            color: var(--dark);
        }

        .hero-section {
            text-align: center;
            padding: 4rem 2rem;
            background: linear-gradient(135deg, var(--primary) 0%, var(--secondary) 100%);
            color: white;
            margin-bottom: 3rem;
            position: relative;
            overflow: hidden;
        }

        .hero-section::before {
            content: "";
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100" preserveAspectRatio="none"><path fill="rgba(255,255,255,0.05)" d="M0,0 L100,0 L100,100 L0,100 Z" /></svg>');
            background-size: cover;
            opacity: 0.1;
        }

        .hero-section h1 {
            font-size: 2.8rem;
            margin-bottom: 1rem;
            font-weight: 700;
            position: relative;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 1rem;
        }

        .hero-section p {
            font-size: 1.2rem;
            opacity: 0.9;
            max-width: 600px;
            margin: 0 auto;
            position: relative;
        }

        .auth-status {
            background: rgba(255, 255, 255, 0.2);
            backdrop-filter: blur(10px);
            border-radius: 50px;
            padding: 0.8rem 1.5rem;
            margin-top: 2rem;
            display: inline-block;
            font-weight: 500;
            position: relative;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 2rem;
        }

        .catalogue-header {
            text-align: center;
            margin-bottom: 3rem;
        }

        .catalogue-header h2 {
            color: var(--secondary);
            font-size: 2.2rem;
            margin-bottom: 1rem;
            font-weight: 600;
        }

        .catalogue-stats {
            color: var(--accent);
            font-size: 1.1rem;
            font-weight: 500;
        }

        .catalogue {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
            gap: 2rem;
            margin-bottom: 3rem;
        }

        .livre-card {
            background: white;
            border-radius: var(--border-radius);
            padding: 1.5rem;
            box-shadow: var(--box-shadow);
            transition: var(--transition);
            border: none;
        }

        .livre-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 15px 30px rgba(0,0,0,0.15);
        }

        .livre-card h3 {
            font-size: 1.4rem;
            color: var(--secondary);
            margin-bottom: 1rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .livre-card p {
            margin-bottom: 0.8rem;
            color: #495057;
        }

        .livre-card strong {
            color: var(--dark);
            font-weight: 500;
        }

        .category-tag {
            background: rgba(67, 97, 238, 0.1);
            color: var(--primary);
            padding: 0.3rem 0.8rem;
            border-radius: 50px;
            font-size: 0.85rem;
            margin-right: 0.5rem;
            margin-bottom: 0.5rem;
            display: inline-block;
        }

        .btn {
            display: inline-block;
            padding: 0.8rem 1.5rem;
            border-radius: 50px;
            font-weight: 500;
            text-decoration: none;
            transition: var(--transition);
            border: none;
            cursor: pointer;
            font-size: 0.95rem;
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

        .btn-secondary {
            background: white;
            color: var(--secondary);
            border: 1px solid var(--secondary);
        }

        .btn-secondary:hover {
            background: #f8f9fa;
            transform: translateY(-2px);
        }

        .empty-state {
            text-align: center;
            padding: 4rem 2rem;
            grid-column: 1 / -1;
        }

        .empty-state h3 {
            font-size: 1.8rem;
            color: var(--secondary);
            margin-bottom: 1rem;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 0.5rem;
        }

        .empty-state p {
            color: #6c757d;
            font-size: 1.1rem;
            max-width: 500px;
            margin: 0 auto;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        @media (max-width: 768px) {
            .hero-section h1 {
                font-size: 2rem;
            }
            
            .hero-section p {
                font-size: 1rem;
            }
            
            .catalogue {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <div class="hero-section">
        <h1><span>📚</span> Bibliothèque</h1>
        <p>Découvrez notre collection de livres et plongez dans l'univers de la connaissance</p>
        <div class="auth-status">
            <c:choose>
                <c:when test="${isLoggedIn}">
                    <span>🟢 Connecté - Vous pouvez emprunter des livres</span>
                </c:when>
                <c:otherwise>
                    <span>🔴 Non connecté - <a href="/login" style="color: var(--warning); text-decoration: none; font-weight: 600;">Se connecter</a> pour emprunter</span>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <div class="container">
        <div class="catalogue-header">
            <h2>Notre Catalogue</h2>
            <p class="catalogue-stats">
                <c:choose>
                    <c:when test="${not empty livres}">
                        ${livres.size()} livre(s) disponible(s) dans notre collection
                    </c:when>
                    <c:otherwise>
                        Aucun livre disponible pour le moment
                    </c:otherwise>
                </c:choose>
            </p>
        </div>
        
        <div class="catalogue">
            <c:forEach var="livre" items="${livres}">
                <div class="livre-card">
                    <h3>${livre.titre}</h3>
                    <p><strong>✍️ Auteur :</strong> ${livre.auteur}</p>
                    <p><strong>📄 Résumé :</strong> ${livre.resume}</p>
                    <p><strong>🏷️ Catégories :</strong></p>
                    <div>
                        <c:forEach var="categorie" items="${livre.categories}">
                            <span class="category-tag">${categorie.libelle}</span>
                        </c:forEach>
                    </div>
                    <div style="margin-top: 1.5rem;">
                        <c:choose>
                            <c:when test="${isLoggedIn}">
                                <form method="get" action="/catalogue/emprunter" style="display: inline;">
                                    <input type="hidden" name="livreId" value="${livre.id}" />
                                    <button type="submit" class="btn btn-primary">Emprunter ce livre</button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <form method="get" action="/login" style="display: inline;">
                                    <input type="hidden" name="redirectTo" value="/catalogue?emprunter=${livre.id}" />
                                    <button type="submit" class="btn btn-secondary">Se connecter pour emprunter</button>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </c:forEach>
            
            <c:if test="${empty livres}">
                <div class="empty-state">
                    <h3><span>📚</span> Aucun livre disponible</h3>
                    <p>Notre catalogue sera bientôt enrichi de nouveaux ouvrages !</p>
                </div>
            </c:if>
        </div>
    </div>
</body>
</html>