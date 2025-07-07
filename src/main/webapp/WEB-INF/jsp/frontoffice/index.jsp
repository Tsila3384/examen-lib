<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Espace Adhérent - Bibliothèque</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
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
            max-width: 1200px;
            margin: 0 auto;
            padding: 2rem;
        }

        .dashboard-header {
            text-align: center;
            margin-bottom: 3rem;
            animation: fadeIn 0.8s ease;
        }

        .dashboard-header h1 {
            font-size: 2.5rem;
            color: var(--secondary);
            margin-bottom: 0.5rem;
            font-weight: 700;
        }

        .dashboard-header p {
            font-size: 1.1rem;
            color: #6c757d;
            max-width: 600px;
            margin: 0 auto;
        }

        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
            gap: 1.5rem;
            margin-bottom: 3rem;
        }

        .dashboard-card {
            background: white;
            border-radius: var(--border-radius);
            padding: 1.5rem;
            box-shadow: var(--box-shadow);
            transition: var(--transition);
            border: none;
            text-align: center;
            display: flex;
            flex-direction: column;
            align-items: center;
            height: 100%;
        }

        .dashboard-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 15px 30px rgba(0,0,0,0.15);
        }

        .dashboard-card h3 {
            font-size: 1.3rem;
            margin: 1rem 0;
            color: var(--secondary);
            font-weight: 600;
        }

        .dashboard-card p {
            color: #6c757d;
            margin-bottom: 1.5rem;
            flex-grow: 1;
        }

        .card-icon {
            font-size: 2.5rem;
            background: linear-gradient(135deg, var(--primary) 0%, var(--accent) 100%);
            -webkit-background-clip: text;
            background-clip: text;
            -webkit-text-fill-color: transparent;
            margin-bottom: 0.5rem;
        }

        .btn {
            display: inline-block;
            padding: 0.6rem 1.5rem;
            border-radius: 50px;
            font-weight: 500;
            text-decoration: none;
            transition: var(--transition);
            border: none;
            cursor: pointer;
            font-size: 0.95rem;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .btn-primary {
            background: var(--primary);
            color: white;
        }

        .btn-primary:hover {
            background: var(--secondary);
            transform: translateY(-2px);
        }

        .btn-success {
            background: var(--success);
            color: white;
        }

        .btn-success:hover {
            background: #3aa8d8;
            transform: translateY(-2px);
        }

        .btn-info {
            background: var(--info);
            color: white;
        }

        .btn-info:hover {
            background: #4a09a0;
            transform: translateY(-2px);
        }

        .btn-secondary {
            background: var(--secondary);
            color: white;
        }

        .btn-secondary:hover {
            background: #3526a8;
            transform: translateY(-2px);
        }

        .btn-warning {
            background: var(--warning);
            color: white;
        }

        .btn-warning:hover {
            background: #e07e0e;
            transform: translateY(-2px);
        }

        .btn-danger {
            background: var(--danger);
            color: white;
            padding: 0.8rem 2rem;
            font-size: 1rem;
            margin-top: 1rem;
        }

        .btn-danger:hover {
            background: #e51779;
            transform: translateY(-2px);
        }

        .logout-section {
            text-align: center;
            margin-top: 3rem;
            padding-top: 2rem;
            border-top: 1px solid rgba(0,0,0,0.1);
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        @media (max-width: 768px) {
            .dashboard-grid {
                grid-template-columns: 1fr;
            }
            
            .dashboard-header h1 {
                font-size: 2rem;
            }
        }
    </style>
</head>
<body>
    <div class="dashboard-container">
        <div class="dashboard-header">
            <h1>Bienvenue dans votre espace adhérent</h1>
            <p>Accédez à toutes les fonctionnalités pour gérer vos emprunts et découvrir notre collection</p>
        </div>
        
        <div class="dashboard-grid">
            <div class="dashboard-card">
                <div class="card-icon">📚</div>
                <h3>Emprunter un livre</h3>
                <p>Explorez notre collection et empruntez les livres qui vous intéressent</p>
                <a href="${pageContext.request.contextPath}/frontoffice/emprunt" class="btn btn-primary">Accéder</a>
            </div>
            
            <div class="dashboard-card">
                <div class="card-icon">⏳</div>
                <h3>Prolonger un prêt</h3>
                <p>Demandez une extension pour vos emprunts en cours avant la date de retour</p>
                <a href="${pageContext.request.contextPath}/frontoffice/prolongation?adherentId=${adherentId}" class="btn btn-success">Prolonger</a>
            </div>
            
            <div class="dashboard-card">
                <div class="card-icon">🔖</div>
                <h3>Réserver un livre</h3>
                <p>Mettez de côté les livres actuellement indisponibles</p>
                <a href="${pageContext.request.contextPath}/frontoffice/reservation" class="btn btn-info">Réserver</a>
            </div>
            
            <div class="dashboard-card">
                <div class="card-icon">🔍</div>
                <h3>Catalogue complet</h3>
                <p>Parcourez l'ensemble de nos ouvrages disponibles</p>
                <a href="/" class="btn btn-secondary">Explorer</a>
            </div>
            
            <div class="dashboard-card">
                <div class="card-icon">🔄</div>
                <h3>Rendre un livre</h3>
                <p>Finalisez votre emprunt en restituant vos livres</p>
                <a href="${pageContext.request.contextPath}/prets/frontoffice/retour?adherentId=${adherentId}" class="btn btn-warning">Restituer</a>
            </div>
        </div>
        
        <div class="logout-section">
            <button type="button" class="btn btn-danger" onclick="window.location.href='${pageContext.request.contextPath}/login'">
                Se déconnecter
            </button>
        </div>
    </div>
</body>
</html>