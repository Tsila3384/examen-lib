<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - Admin Bibliothèque</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        :root {
            --bg: #1e1e2f;
            --bg-alt: #2a2a3b;
            --primary: #4fc3f7;
            --accent: #ffca28;
            --text: #f5f5f5;
            --muted: #b0bec5;
            --danger: #ef5350;
            --card-radius: 12px;
        }

        body {
            margin: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: var(--bg);
            color: var(--text);
        }

        .admin-container {
            max-width: 1100px;
            margin: auto;
            padding: 2rem;
        }

        .admin-header {
            text-align: center;
            padding-bottom: 1.5rem;
            border-bottom: 1px solid var(--muted);
        }

        .admin-header h1 {
            margin: 0;
            font-size: 2.5rem;
            color: var(--primary);
        }

        .admin-header p {
            font-size: 1.1rem;
            color: var(--muted);
        }

        .admin-nav {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
            gap: 1rem;
            margin: 2rem 0;
        }

        .btn {
            background-color: var(--bg-alt);
            color: var(--primary);
            border: 1px solid var(--primary);
            padding: 1rem;
            text-align: center;
            font-size: 1rem;
            text-decoration: none;
            border-radius: var(--card-radius);
            transition: 0.3s ease;
            display: block;
        }

        .btn:hover {
            background-color: var(--primary);
            color: var(--bg);
        }

        .btn-danger {
            background-color: transparent;
            border: 1px solid var(--danger);
            color: var(--danger);
        }

        .btn-danger:hover {
            background-color: var(--danger);
            color: #fff;
        }

        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 1.5rem;
            margin-top: 2rem;
        }

        .stat-card {
            background-color: var(--bg-alt);
            padding: 1.5rem;
            border-radius: var(--card-radius);
            text-align: center;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.3);
        }

        .stat-card h3 {
            font-size: 2.5rem;
            margin: 0 0 1rem 0;
        }

        .stat-card p {
            font-size: 1.1rem;
            color: var(--muted);
        }

        .logout-container {
            text-align: center;
            padding: 2rem 0;
            margin-top: 2rem;
            border-top: 1px solid var(--muted);
        }

        @media (max-width: 600px) {
            .btn {
                font-size: 0.9rem;
                padding: 0.75rem;
            }

            .admin-header h1 {
                font-size: 2rem;
            }
        }
    </style>
</head>
<body>
<div class="admin-container">
    <div class="admin-header">
        <h1>📚 Admin Bibliothèque</h1>
        <p>Bienvenue sur le tableau de bord</p>
    </div>

    <div class="admin-nav">
        <a href="${pageContext.request.contextPath}/backoffice/prets-attente" class="btn">📋 Prêts en attente</a>
        <a href="${pageContext.request.contextPath}/backoffice/prolongations/attente" class="btn">⏰ Prolongations</a>
        <a href="${pageContext.request.contextPath}/backoffice/reservations/attente" class="btn">📅 Réservations</a>
        <a href="${pageContext.request.contextPath}/backoffice/pret-direct" class="btn">🚀 Prêt direct</a>
        <a href="${pageContext.request.contextPath}/backoffice/prets-en-cours" class="btn">📖 Prêts en cours</a>
    </div>

    <div class="stats-grid">
        <div class="stat-card">
            <h3>📋</h3>
            <p>Demandes en attente</p>
        </div>
        <div class="stat-card">
            <h3>📚</h3>
            <p>Livres en circulation</p>
        </div>
        <div class="stat-card">
            <h3>👥</h3>
            <p>Adhérents actifs</p>
        </div>
        <div class="stat-card">
            <h3>⚠️</h3>
            <p>Retards signalés</p>
        </div>
    </div>

    <div class="logout-container">
        <button type="button" class="btn btn-danger" onclick="window.location.href='${pageContext.request.contextPath}/login'">
            🚪 Déconnexion
        </button>
    </div>
</div>
</body>
</html>
