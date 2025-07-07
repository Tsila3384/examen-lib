<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Prolongements en attente - Admin</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        :root {
            --bg: #12141e;
            --card: #1f2333;
            --text: #f1f1f1;
            --muted: #9aa0ac;
            --primary: #6c5ce7;
            --success: #2ed573;
            --danger: #ff6b81;
            --border: #2f3542;
            --radius: 10px;
        }

        body {
            margin: 0;
            padding: 2rem;
            font-family: 'Segoe UI', sans-serif;
            background-color: var(--bg);
            color: var(--text);
        }

        .admin-container {
            max-width: 1100px;
            margin: auto;
            background-color: var(--card);
            padding: 2rem;
            border-radius: var(--radius);
            box-shadow: 0 0 20px rgba(0,0,0,0.3);
        }

        .admin-header {
            text-align: center;
            margin-bottom: 2rem;
        }

        .admin-header h1 {
            margin: 0;
            font-size: 2rem;
            color: var(--primary);
        }

        .admin-header p {
            color: var(--muted);
        }

        .btn {
            padding: 0.6rem 1.2rem;
            font-weight: bold;
            border: none;
            border-radius: var(--radius);
            cursor: pointer;
            transition: 0.2s ease-in-out;
            text-decoration: none;
            display: inline-block;
        }

        .btn-success {
            background-color: var(--success);
            color: #000;
        }

        .btn-danger {
            background-color: var(--danger);
            color: #fff;
        }

        .btn-secondary {
            background-color: var(--border);
            color: var(--text);
        }

        .btn:hover {
            opacity: 0.85;
        }

        .alert {
            padding: 1rem;
            border-radius: var(--radius);
            margin-bottom: 1rem;
            font-weight: bold;
        }

        .alert-success {
            background-color: #2ed57333;
            border: 1px solid var(--success);
            color: var(--success);
        }

        .alert-danger {
            background-color: #ff6b8133;
            border: 1px solid var(--danger);
            color: var(--danger);
        }

        .data-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 1rem;
            background-color: #2a2f44;
            border-radius: var(--radius);
            overflow: hidden;
        }

        .data-table th, .data-table td {
            padding: 1rem;
            text-align: left;
            border-bottom: 1px solid var(--border);
        }

        .data-table th {
            background-color: #3a3f57;
            color: var(--primary);
            font-weight: bold;
        }

        .data-table tr:hover {
            background-color: #353b52;
        }

        .action-section {
            text-align: center;
            margin-top: 2rem;
        }

        @media (max-width: 700px) {
            .data-table thead {
                display: none;
            }

            .data-table, .data-table tbody, .data-table tr, .data-table td {
                display: block;
                width: 100%;
            }

            .data-table tr {
                margin-bottom: 1rem;
                border-bottom: 2px solid var(--border);
                padding-bottom: 1rem;
            }

            .data-table td {
                padding: 0.5rem 1rem;
                text-align: left;
                position: relative;
            }

            .data-table td::before {
                content: attr(data-label);
                font-weight: bold;
                display: block;
                margin-bottom: 0.5rem;
                color: var(--muted);
            }
        }
    </style>
</head>
<body class="admin-body">
<div class="container admin-container">
    <div class="admin-header">
        <h1>📆 Prolongements en attente</h1>
        <p>Gérez les demandes de prolongation des adhérents</p>
    </div>

    <div class="content-section">
        <c:if test="${not empty message}">
            <div class="alert alert-success">✅ ${message}</div>
        </c:if>
        <c:if test="${not empty erreur}">
            <div class="alert alert-danger">❌ ${erreur}</div>
        </c:if>

        <table class="data-table">
            <thead>
            <tr>
                <th>👤 Adhérent</th>
                <th>📚 Livre</th>
                <th>📅 Date retour actuelle</th>
                <th>📅 Nouvelle date</th>
                <th>⚙️ Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="prolong" items="${prolongations}">
                <tr>
                    <td data-label="Adhérent">${prolong.pret.adherent.nom} ${prolong.pret.adherent.prenom}</td>
                    <td data-label="Livre">${prolong.pret.exemplaire.livre.titre}</td>
                    <td data-label="Date actuelle">${prolong.pret.dateRetourPrevue}</td>
                    <td data-label="Nouvelle date">${prolong.nouvelleDateRetour}</td>
                    <td data-label="Action">
                        <form method="post" action="${pageContext.request.contextPath}/backoffice/prolongations/valider" style="display:inline;">
                            <input type="hidden" name="prolongationId" value="${prolong.id}" />
                            <input type="hidden" name="action" value="accepter" />
                            <button type="submit" class="btn btn-success">✅ Accepter</button>
                        </form>
                        <form method="post" action="${pageContext.request.contextPath}/backoffice/prolongations/valider" style="display:inline;">
                            <input type="hidden" name="prolongationId" value="${prolong.id}" />
                            <input type="hidden" name="action" value="refuser" />
                            <button type="submit" class="btn btn-danger">❌ Refuser</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <div class="action-section">
            <a href="${pageContext.request.contextPath}/backoffice" class="btn btn-secondary">⬅️ Retour Administration</a>
        </div>
    </div>
</div>
</body>
</html>
