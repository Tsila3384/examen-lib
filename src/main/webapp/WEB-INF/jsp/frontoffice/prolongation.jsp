<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Prolonger un prêt - Espace Adhérent</title>
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
            max-width: 800px;
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
        }

        .dashboard-header p {
            color: #6c757d;
            font-size: 1.1rem;
        }

        .form-container {
            max-width: 600px;
            margin: 0 auto;
        }

        .form-group {
            margin-bottom: 1.5rem;
        }

        .form-label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: 500;
            color: var(--secondary);
        }

        .form-input {
            width: 100%;
            padding: 0.8rem 1rem;
            border: 1px solid #ddd;
            border-radius: var(--border-radius);
            font-family: 'Poppins', sans-serif;
            font-size: 1rem;
            transition: var(--transition);
        }

        .form-input:focus {
            outline: none;
            border-color: var(--accent);
            box-shadow: 0 0 0 3px rgba(67, 97, 238, 0.2);
        }

        select.form-input {
            appearance: none;
            background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='16' height='16' fill='%234361ee' viewBox='0 0 16 16'%3E%3Cpath d='M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z'/%3E%3C/svg%3E");
            background-repeat: no-repeat;
            background-position: right 1rem center;
            background-size: 16px 12px;
            padding-right: 2.5rem;
        }

        .form-actions {
            display: flex;
            gap: 1rem;
            justify-content: center;
            margin-top: 2rem;
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

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        @media (max-width: 768px) {
            .form-actions {
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
            <h1>⏳ Demander une prolongation de prêt</h1>
            <p>Prolongez la durée de vos emprunts en cours</p>
        </div>
        
        <div class="form-container">
            <c:if test="${not empty message}">
                <div class="alert alert-success">✅ ${message}</div>
            </c:if>
            <c:if test="${not empty erreur}">
                <div class="alert alert-danger">❌ ${erreur}</div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/frontoffice/prolongation" method="post">
                <input type="hidden" name="adherentId" value="${adherentId}"/>
                
                <div class="form-group">
                    <label for="pretId" class="form-label">Sélectionnez un prêt à prolonger :</label>
                    <select name="pretId" id="pretId" class="form-input" required>
                        <option value="">-- Choisir un prêt --</option>
                        <c:forEach var="pret" items="${prets}">
                            <option value="${pret.id}">
                                ${pret.exemplaire.livre.titre} | Retour prévu le ${pret.dateRetourPrevue}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Demander la prolongation</button>
                    <a href="${pageContext.request.contextPath}/frontoffice?adherentId=${adherentId}" class="btn btn-secondary">Retour accueil</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>