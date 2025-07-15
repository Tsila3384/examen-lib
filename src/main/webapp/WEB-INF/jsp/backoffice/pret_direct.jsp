<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Prêt direct - Admin</title>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <style>
                :root {
                    --bg: #141726;
                    --card: #1f2235;
                    --primary: #6c5ce7;
                    --text: #f1f2f6;
                    --muted: #a4b0be;
                    --success: #2ed573;
                    --danger: #ff6b81;
                    --secondary: #57606f;
                    --border-radius: 10px;
                }

                body {
                    margin: 0;
                    font-family: 'Segoe UI', sans-serif;
                    background-color: var(--bg);
                    color: var(--text);
                    padding: 2rem;
                }

                .admin-container {
                    max-width: 700px;
                    margin: auto;
                    background-color: var(--card);
                    padding: 2.5rem;
                    border-radius: var(--border-radius);
                    box-shadow: 0 0 30px rgba(0, 0, 0, 0.3);
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

                .form-container {
                    display: flex;
                    flex-direction: column;
                    gap: 1.5rem;
                }

                .form-group {
                    position: relative;
                }

                .form-label {
                    position: absolute;
                    top: -0.75rem;
                    left: 0.75rem;
                    background-color: var(--card);
                    padding: 0 0.4rem;
                    font-size: 0.9rem;
                    color: var(--muted);
                }

                .form-input {
                    width: 100%;
                    padding: 0.9rem 0.75rem;
                    background-color: #2f3542;
                    border: 1px solid var(--muted);
                    border-radius: var(--border-radius);
                    color: var(--text);
                    font-size: 1rem;
                }

                .form-input:focus {
                    outline: none;
                    border-color: var(--primary);
                }

                .form-actions {
                    display: flex;
                    justify-content: space-between;
                    margin-top: 1rem;
                }

                .btn {
                    padding: 0.8rem 1.5rem;
                    border: none;
                    border-radius: var(--border-radius);
                    font-weight: bold;
                    cursor: pointer;
                    transition: 0.3s ease-in-out;
                }

                .btn-success {
                    background-color: var(--success);
                    color: #000;
                }

                .btn-success:hover {
                    background-color: #28a745;
                }

                .btn-secondary {
                    background-color: var(--secondary);
                    color: #fff;
                }

                .btn-secondary:hover {
                    background-color: #2f3542;
                }

                .alert {
                    margin-top: 1.5rem;
                    padding: 1rem;
                    border-radius: var(--border-radius);
                    font-weight: bold;
                }

                .alert-success {
                    background-color: #2ed57333;
                    color: var(--success);
                    border: 1px solid var(--success);
                }

                .alert-danger {
                    background-color: #ff6b8133;
                    color: var(--danger);
                    border: 1px solid var(--danger);
                }

                @media (max-width: 600px) {
                    .form-actions {
                        flex-direction: column;
                        gap: 1rem;
                    }
                }
            </style>
        </head>

        <body>
            <div class="admin-container">
                <div class="admin-header">
                    <h1>📖 Prêt direct</h1>
                    <p>Créer un prêt instantanément</p>
                </div>

                <div class="content-section">
                    <form method="post" action="${pageContext.request.contextPath}/backoffice/pret-direct"
                        class="form-container">

                        <div class="form-group">
                            <label for="adherentInput" class="form-label">Adhérent</label>
                            <input class="form-input" list="adherentsList" id="adherentInput" name="adherentId" required
                                autocomplete="off" placeholder="ID ou nom...">
                            <datalist id="adherentsList">
                                <c:forEach var="adherent" items="${adherents}">
                                    <option value="${adherent.id}"
                                        label="${adherent.nom} ${adherent.prenom} (${adherent.email})"></option>
                                </c:forEach>
                            </datalist>
                        </div>

                        <div class="form-group">
                            <label for="livreInput" class="form-label">Livre</label>
                            <input class="form-input" list="livresList" id="livreInput" name="livreId" required
                                autocomplete="off" placeholder="ID ou titre...">
                            <datalist id="livresList">
                                <c:forEach var="livre" items="${livres}">
                                    <option value="${livre.id}" label="${livre.titre} (${livre.auteur})"></option>
                                </c:forEach>
                            </datalist>
                        </div>

                        <div class="form-group">
                            <label for="typePret" class="form-label">Type de prêt</label>
                            <select class="form-input" id="typePret" name="typePret" required>
                                <option value="sur place">Sur place</option>
                                <option value="emporte">À emporter</option>
                            </select>
                        </div>


                        <div class="form-group">
                            <label for="dateEmprunt">Date d'emprunt :</label>
                            <input type="datetime-local" id="dateEmprunt" name="dateEmprunt">
                        </div>

                        <div class="form-group">
                            <label for="dateRetour">Date de retour :</label>
                            <input type="datetime-local" id="dateRetour" name="dateRetour">
                        </div>

                        <div class="form-actions">
                            <button type="submit" class="btn btn-success">✅ Valider</button>
                            <a href="${pageContext.request.contextPath}/backoffice" class="btn btn-secondary">↩️
                                Annuler</a>
                        </div>
                    </form>

                    <c:if test="${not empty message}">
                        <div class="alert alert-success">${message}</div>
                    </c:if>
                    <c:if test="${not empty erreur}">
                        <div class="alert alert-danger">${erreur}</div>
                    </c:if>
                </div>
            </div>
        </body>

        </html>