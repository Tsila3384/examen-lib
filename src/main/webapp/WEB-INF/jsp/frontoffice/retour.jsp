<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Rendre un livre - Bibliothèque</title>
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

        .dashboard-container {
            max-width: 1000px;
            margin: 2rem auto;
            background: white;
            border-radius: var(--border-radius);
            box-shadow: var(--box-shadow);
            padding: 2rem;
            animation: fadeIn 0.8s ease;
        }

        .dashboard-header {
            text-align: center;
            margin-bottom: 2rem;
            padding-bottom: 1rem;
            border-bottom: 1px solid rgba(0,0,0,0.1);
        }

        .dashboard-header h1 {
            font-size: 2rem;
            color: var(--secondary);
            margin-bottom: 0.5rem;
            font-weight: 600;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 0.5rem;
        }

        .dashboard-header p {
            color: #6c757d;
            font-size: 1.1rem;
        }

        .alert {
            padding: 1rem;
            border-radius: var(--border-radius);
            margin-bottom: 1.5rem;
            font-weight: 500;
        }

        .alert-success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }

        .alert-danger {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }

        .book-table {
            width: 100%;
            border-collapse: collapse;
            margin: 1.5rem 0;
            overflow: hidden;
            box-shadow: 0 0 20px rgba(0,0,0,0.1);
            border-radius: var(--border-radius);
        }

        .book-table thead tr {
            background-color: var(--secondary);
            color: white;
            text-align: left;
        }

        .book-table th,
        .book-table td {
            padding: 1rem;
        }

        .book-table tbody tr {
            border-bottom: 1px solid #f0f0f0;
            transition: var(--transition);
        }

        .book-table tbody tr:nth-of-type(even) {
            background-color: #f9f9f9;
        }

        .book-table tbody tr:last-of-type {
            border-bottom: 2px solid var(--secondary);
        }

        .book-table tbody tr:hover {
            background-color: #f1f3ff;
        }

        .btn {
            display: inline-block;
            padding: 0.6rem 1.2rem;
            border-radius: 50px;
            font-weight: 500;
            text-decoration: none;
            transition: var(--transition);
            border: none;
            cursor: pointer;
            font-size: 0.9rem;
            text-align: center;
        }

        .btn-warning {
            background: var(--warning);
            color: white;
        }

        .btn-warning:hover {
            background: #e07e0e;
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

        .action-form {
            margin: 0;
        }

        .text-center {
            text-align: center;
        }

        .mt-3 {
            margin-top: 1.5rem;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        @media (max-width: 768px) {
            .book-table {
                display: block;
                overflow-x: auto;
            }
            
            .dashboard-container {
                padding: 1rem;
            }
        }
    </style>
</head>
<body>
    <div class="dashboard-container">
        <div class="dashboard-header">
            <h1><span>📚</span> Rendre un livre</h1>
            <p>Sélectionnez un prêt à rendre à la bibliothèque</p>
        </div>

        <c:if test="${not empty message}">
            <div class="alert alert-success">✅ ${message}</div>
        </c:if>
        <c:if test="${not empty erreur}">
            <div class="alert alert-danger">❌ ${erreur}</div>
        </c:if>

        <table class="book-table">
            <thead>
                <tr>
                    <th>Livre</th>
                    <th>Date d'emprunt</th>
                    <th>Date retour prévue</th>
                    <th>Statut</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="pret" items="${prets}">
                    <tr>
                        <td><strong>${pret.exemplaire.livre.titre}</strong></td>
                        <td>${pret.dateEmprunt}</td>
                        <td>${pret.dateRetourPrevue}</td>
                        <td>
                            <span style="display: inline-block; padding: 0.3rem 0.6rem; border-radius: 50px; 
                                  background-color: ${pret.statutPret.libelle == 'En cours' ? '#e2f0fd' : '#ffe8cc'}; 
                                  color: ${pret.statutPret.libelle == 'En cours' ? '#1a56db' : '#d97706'};">
                                ${pret.statutPret.libelle}
                            </span>
                        </td>
                        <td>
                            <form method="post" action="${pageContext.request.contextPath}/prets/frontoffice/retour/rendre" class="action-form">
                                <input type="hidden" name="pretId" value="${pret.id}" />
                                <input type="hidden" name="adherentId" value="${adherentId}" />
                                <button type="submit" class="btn btn-warning">Rendre</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="text-center mt-3">
            <a href="${pageContext.request.contextPath}/frontoffice" class="btn btn-secondary">Retour à l'accueil</a>
        </div>
    </div>
</body>
</html>