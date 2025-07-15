<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Prêts en attente - Admin</title>
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
            --info: #00cec9;
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

        .admin-header h2 {
            margin: 0;
            font-size: 1.8rem;
            color: var(--primary);
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

        .alert-info {
            background-color: #00cec933;
            border: 1px solid var(--info);
            color: var(--info);
        }

        .alert-danger {
            background-color: #ff6b8133;
            border: 1px solid var(--danger);
            color: var(--danger);
        }

        .admin-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 1rem;
            background-color: #2a2f44;
            border-radius: var(--radius);
            overflow: hidden;
        }

        .admin-table th, .admin-table td {
            padding: 1rem;
            text-align: left;
            border-bottom: 1px solid var(--border);
        }

        .admin-table th {
            background-color: #3a3f57;
            color: var(--primary);
            font-weight: bold;
        }

        .admin-table tr:hover {
            background-color: #353b52;
        }

        .status-badge {
            display: inline-block;
            padding: 0.3rem 0.6rem;
            border-radius: 12px;
            font-size: 0.8rem;
            background-color: #444;
            color: var(--muted);
        }

        .pret-type {
            font-size: 0.8rem;
            border-radius: 20px;
            padding: 0.3rem 0.7rem;
            font-weight: bold;
            display: inline-block;
        }

        .pret-emporte {
            background-color: #2ed57333;
            color: #2ed573;
        }

        .pret-place {
            background-color: #3498db33;
            color: #3498db;
        }

        .mb-3 {
            margin-bottom: 1rem;
            display: inline-block;
        }

        small {
            color: var(--muted);
        }

        @media (max-width: 700px) {
            .admin-table thead {
                display: none;
            }

            .admin-table, .admin-table tbody, .admin-table tr, .admin-table td {
                display: block;
                width: 100%;
            }

            .admin-table tr {
                margin-bottom: 1rem;
                border-bottom: 2px solid var(--border);
                padding-bottom: 1rem;
            }

            .admin-table td {
                padding: 0.5rem 1rem;
                text-align: left;
                position: relative;
            }

            .admin-table td::before {
                content: attr(data-label);
                font-weight: bold;
                display: block;
                margin-bottom: 0.5rem;
                color: var(--muted);
            }
        }
    </style>
</head>
<body>
<div class="container admin-container">
    <div class="admin-header">
        <h2>📋 Prêts en attente de validation</h2>
    </div>

    <div>
        <a href="${pageContext.request.contextPath}/backoffice" class="btn btn-secondary mb-3">⬅️ Retour Administration</a>

        <c:if test="${empty prets}">
            <div class="alert alert-info">
                ℹ️ Aucun prêt en attente de validation.
            </div>
        </c:if>

        <c:if test="${not empty prets}">
            <c:if test="${not empty erreurValidation}">
                <div class="alert alert-danger">❌ ${erreurValidation}</div>
            </c:if>

            <table class="admin-table">
                <thead>
                    <tr>
                        <th>👤 Adhérent</th>
                        <th>📚 Livre</th>
                        <th>📖 Exemplaire</th>
                        <th>📅 Date d'Emprunt</th>
                        <th>📅 Date de Retour</th>
                        <th>🏷️ Type</th>
                        <th>⚙️ Actions</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="pret" items="${prets}">
                    <tr>
                        <td data-label="Adhérent">
                            <strong>${pret.adherent.nom} ${pret.adherent.prenom}</strong><br>
                            <small>${pret.adherent.email}</small>
                        </td>
                        <td data-label="Livre">
                            <strong>${pret.exemplaire.livre.titre}</strong><br>
                            <small>par ${pret.exemplaire.livre.auteur}</small>
                        </td>
                        <td data-label="Exemplaire">
                            <span class="status-badge">Ex. #${pret.exemplaire.id}</span>
                        </td>
                        <td data-label="Date">${pret.dateEmprunt}</td>
                         <td data-label="Date">${pret.dateRetourPrevue}</td>
                        <td data-label="Type">
                            <c:choose>
                                <c:when test="${pret.typePret == 'emporte'}">
                                    <span class="pret-type pret-emporte">📦 À emporter</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="pret-type pret-place">🏛️ Sur place</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td data-label="Actions">
                            <div style="display: flex; gap: 0.5rem;">
                                <form method="post" action="${pageContext.request.contextPath}/backoffice/valider-pret">
                                    <input type="hidden" name="pretId" value="${pret.id}"/>
                                    <button type="submit" class="btn btn-success">✅ Valider</button>
                                </form>
                                <form method="post" action="${pageContext.request.contextPath}/backoffice/refuser-pret">
                                    <input type="hidden" name="pretId" value="${pret.id}"/>
                                    <button type="submit" class="btn btn-danger">❌ Refuser</button>
                                </form>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</div>
</body>
</html>
