<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Prêts en cours - Admin</title>
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
            color: var(--primary);
            margin: 0;
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
        }

        .mb-3 {
            margin-bottom: 1rem;
            display: inline-block;
        }

        small {
            color: var(--muted);
        }

        .btn-disabled {
            background-color: #6c757d;
            color: #fff;
            cursor: not-allowed;
            opacity: 0.6;
            padding: 0.6rem 1.2rem;
            border-radius: var(--radius);
            display: inline-block;
        }

        .btn-disabled small {
            display: block;
            font-size: 0.7rem;
            color: #e9ecef;
            margin-top: 0.2rem;
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
        <h2>📖 Prêts en cours</h2>
    </div>

    <div>
        <a href="${pageContext.request.contextPath}/backoffice" class="btn btn-secondary mb-3">⬅️ Retour Administration</a>

        <c:if test="${not empty message}">
            <div class="alert alert-success">✅ ${message}</div>
        </c:if>
        <c:if test="${not empty erreur}">
            <div class="alert alert-danger">❌ ${erreur}</div>
        </c:if>

        <table class="admin-table">
            <thead>
            <tr>
                <th>👤 Adhérent</th>
                <th>📚 Livre</th>
                <th>📅 Date limite</th>
                <th>⚙️ Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="dto" items="${pretsEnCours}">
                <tr>
                    <td data-label="Adhérent">
                        <strong>${dto.pret.adherent.nom} ${dto.pret.adherent.prenom}</strong><br>
                        <small>${dto.pret.adherent.email}</small>
                    </td>
                    <td data-label="Livre">
                        <strong>${dto.pret.exemplaire.livre.titre}</strong><br>
                        <small>Ex. #${dto.pret.exemplaire.id}</small>
                    </td>
                    <td data-label="Date limite">
                        <div>
                            <strong><c:out value="${dto.dateLimite}"/></strong>
                            <small style="display:block; font-size:0.8rem;">(limite réelle)</small>
                            <c:if test="${dto.pret.statutPret.libelle eq 'en prolongement'}">
                                <span class="status-badge" style="background: rgba(218, 165, 32, 0.2); color: #b8860b;">⏰ Prolongé</span>
                            </c:if>
                        </div>
                    </td>
                    <td data-label="Action">
                        <c:choose>
                            <c:when test="${dto.penaliserActive}">
                                <form method="post" action="${pageContext.request.contextPath}/backoffice/penaliser-pret">
                                    <input type="hidden" name="pretId" value="${dto.pret.id}"/>
                                    <button type="submit" class="btn btn-danger">⚠️ Pénaliser</button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <div class="btn-disabled">
                                    🚫 Pénaliser
                                    <small>
                                        <c:choose>
                                            <c:when test="${now le dto.dateLimite}">(Non en retard)</c:when>
                                            <c:when test="${dto.adherentPenalise}">(Déjà pénalisé)</c:when>
                                            <c:when test="${dto.pret.statutPret.libelle eq 'en prolongement'}">(En prolongement)</c:when>
                                            <c:otherwise>(Indisponible)</c:otherwise>
                                        </c:choose>
                                    </small>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
