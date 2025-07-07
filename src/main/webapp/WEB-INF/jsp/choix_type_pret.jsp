<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Choix du type de prêt - Bibliothèque</title>
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
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0;
            padding: 1rem;
        }

        .pret-container {
            background: white;
            border-radius: var(--border-radius);
            padding: 2.5rem;
            box-shadow: var(--box-shadow);
            max-width: 500px;
            width: 100%;
            text-align: center;
            animation: fadeIn 0.5s ease;
        }

        .pret-container h2 {
            color: var(--secondary);
            margin-bottom: 1.5rem;
            font-size: 1.8rem;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 0.5rem;
        }

        .livre-info {
            background: rgba(67, 97, 238, 0.05);
            border-radius: var(--border-radius);
            padding: 1.5rem;
            margin-bottom: 2rem;
            border: 1px solid rgba(67, 97, 238, 0.1);
            text-align: left;
        }

        .livre-info h3 {
            color: var(--secondary);
            margin-bottom: 0.5rem;
            font-size: 1.3rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .livre-info p {
            color: #495057;
            margin-bottom: 0;
        }

        .pret-form {
            text-align: left;
        }

        .form-label {
            display: block;
            margin-bottom: 1rem;
            font-weight: 500;
            color: var(--secondary);
        }

        .type-selector {
            display: grid;
            gap: 1rem;
            margin: 1.5rem 0;
        }

        .type-option {
            display: flex;
            align-items: center;
            padding: 1.2rem;
            background: white;
            border: 2px solid #e9ecef;
            border-radius: var(--border-radius);
            cursor: pointer;
            transition: var(--transition);
        }

        .type-option:hover {
            border-color: var(--accent);
            background: rgba(72, 149, 239, 0.05);
        }

        .type-option input[type="radio"] {
            margin-right: 1rem;
            appearance: none;
            width: 18px;
            height: 18px;
            border: 2px solid #dee2e6;
            border-radius: 50%;
            transition: var(--transition);
            position: relative;
        }

        .type-option input[type="radio"]:checked {
            border-color: var(--primary);
            background-color: var(--primary);
        }

        .type-option input[type="radio"]:checked::after {
            content: "";
            position: absolute;
            top: 3px;
            left: 3px;
            width: 8px;
            height: 8px;
            background: white;
            border-radius: 50%;
        }

        .type-option.selected {
            border-color: var(--primary);
            background: rgba(67, 97, 238, 0.05);
        }

        .type-details {
            flex: 1;
        }

        .type-title {
            font-weight: 600;
            color: var(--dark);
            margin-bottom: 0.3rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .type-description {
            color: #6c757d;
            font-size: 0.9rem;
        }

        .actions {
            display: flex;
            gap: 1rem;
            margin-top: 2rem;
            justify-content: center;
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

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        @media (max-width: 576px) {
            .pret-container {
                padding: 1.5rem;
            }
            
            .actions {
                flex-direction: column;
            }
            
            .btn {
                width: 100%;
            }
        }
    </style>
</head>
<body>
    <div class="pret-container">
        <h2><span>📚</span> Emprunter un livre</h2>
        
        <div class="livre-info">
            <h3><span>📖</span> ${livre.titre}</h3>
            <p><strong>✍️ Auteur :</strong> ${livre.auteur}</p>
        </div>
        
        <form method="post" action="/prets/emprunter" class="pret-form">
            <input type="hidden" name="livreId" value="${livre.id}" />
            
            <label class="form-label">
                Choisissez le type de prêt :
            </label>
            
            <div class="type-selector">
                <label class="type-option" onclick="selectType(this)">
                    <input type="radio" name="typePret" value="emporte" required>
                    <div class="type-details">
                        <div class="type-title"><span>📦</span> À emporter</div>
                        <div class="type-description">Emprunter le livre pour le lire chez vous (durée : 2 semaines)</div>
                    </div>
                </label>
                
                <label class="type-option" onclick="selectType(this)">
                    <input type="radio" name="typePret" value="sur place" required>
                    <div class="type-details">
                        <div class="type-title"><span>🏛️</span> Sur place</div>
                        <div class="type-description">Consulter le livre uniquement dans la bibliothèque</div>
                    </div>
                </label>
            </div>
            
            <div class="actions">
                <button type="submit" class="btn btn-primary">
                    <span>✅</span> Valider l'emprunt
                </button>
                <a href="/" class="btn btn-secondary">
                    <span>❌</span> Annuler
                </a>
            </div>
        </form>
    </div>
    
    <script>
        function selectType(element) {
            // Enlever la classe selected de tous les éléments
            document.querySelectorAll('.type-option').forEach(opt => {
                opt.classList.remove('selected');
            });
            
            // Ajouter la classe selected à l'élément cliqué
            element.classList.add('selected');
            
            // Cocher le radio button associé
            const radio = element.querySelector('input[type="radio"]');
            radio.checked = true;
        }
        
        // Gérer la sélection par défaut si une option est déjà cochée
        document.addEventListener('DOMContentLoaded', function() {
            const checkedRadio = document.querySelector('input[type="radio"]:checked');
            if (checkedRadio) {
                selectType(checkedRadio.closest('.type-option'));
            }
        });
    </script>
</body>
</html>