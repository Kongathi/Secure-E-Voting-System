document.addEventListener('DOMContentLoaded', () => {
    const API_BASE_URL = 'http://localhost:8080/api';

    const showMessage = (elementId, text, type) => {
        const el = document.getElementById(elementId);
        el.textContent = text;
        el.className = `message ${type}`;
    };
// Add this new code to the TOP of your existing app.js file

document.addEventListener('DOMContentLoaded', () => {
    // --- DYNAMIC UI ENHANCEMENTS ---

    // 1. Dynamic Header on Scroll
    const header = document.getElementById('main-header');
    if (header) {
        window.addEventListener('scroll', () => {
            if (window.scrollY > 50) {
                header.classList.add('scrolled');
            } else {
                header.classList.remove('scrolled');
            }
        });
    }

    // 2. Reveal Elements on Scroll
    const revealElements = document.querySelectorAll('.reveal');
    const revealObserver = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('visible');
            }
        });
    }, {
        threshold: 0.1
    });

    revealElements.forEach(el => revealObserver.observe(el));


    // --- Your existing page-specific logic goes here ---
    // (The code for login, voting, results, and admin pages remains the same)
    
    const API_BASE_URL = 'http://localhost:8080/api';
    // ... etc.
});
    // --- Voter Login Page Logic ---
    if (document.body.id === 'login-page') {
        const loginForm = document.getElementById('voter-login-form');
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const voterId = document.getElementById('voterId').value;
            if (!voterId || isNaN(voterId)) {
                showMessage('login-message', 'Please enter a valid numeric Voter ID.', 'error');
                return;
            }
            
            // Check if voter exists by trying to fetch their status
            try {
                const response = await fetch(`${API_BASE_URL}/voters/${voterId}`);
                if (response.ok) {
                    sessionStorage.setItem('voterId', voterId);
                    window.location.href = `vote.html`;
                } else {
                    showMessage('login-message', 'Voter ID not found. Please register or check your ID.', 'error');
                }
            } catch (error) {
                console.error('Error authenticating voter:', error);
                showMessage('login-message', 'Could not connect to the server. Please try again later.', 'error');
            }
        });
    }

    // --- Voting Page Logic ---
    if (document.body.id === 'vote-page') {
        const voterId = sessionStorage.getItem('voterId');
        if (!voterId) {
            window.location.href = 'login.html';
            return;
        }

        const candidateList = document.getElementById('candidate-list');
        const selectionText = document.getElementById('selection-text');
        const submitVoteBtn = document.getElementById('submit-vote-btn');
        let selectedCandidateName = null;

        const loadCandidates = async () => {
            try {
                // Check if voter has already voted
                const hasVotedResponse = await fetch(`${API_BASE_URL}/votes/hasVoted?voterId=${voterId}`);
                const hasVoted = await hasVotedResponse.json();

                if (hasVoted) {
                    candidateList.innerHTML = `<h2>Thank you.</h2><p>Our records show that you have already cast your vote.</p>`;
                    document.querySelector('.vote-submission').style.display = 'none';
                    return;
                }

                const candidatesResponse = await fetch(`${API_BASE_URL}/candidates`);
                const candidates = await candidatesResponse.json();
                
                candidateList.innerHTML = '';
                candidates.forEach(c => {
                    const card = document.createElement('div');
                    card.className = 'candidate-card';
                    card.dataset.name = c.name;
                    card.innerHTML = `
                        <img src="images/placeholder.png" alt="Candidate">
                        <h3>${c.name}</h3>
                        <p>${c.party}</p>
                    `;
                    card.addEventListener('click', () => {
                        document.querySelectorAll('.candidate-card').forEach(c => c.classList.remove('selected'));
                        card.classList.add('selected');
                        selectedCandidateName = c.name;
                        selectionText.textContent = `Selected: ${selectedCandidateName}`;
                        submitVoteBtn.disabled = false;
                    });
                    candidateList.appendChild(card);
                });

            } catch (error) {
                console.error('Error loading candidates:', error);
                candidateList.innerHTML = '<p class="message error">Failed to load candidates. Please refresh the page.</p>';
            }
        };

        submitVoteBtn.addEventListener('click', async () => {
            if (!selectedCandidateName) return;

            if (confirm(`Are you sure you want to vote for ${selectedCandidateName}? This action is final.`)) {
                try {
                    const response = await fetch(`${API_BASE_URL}/votes`, {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({ voterId: parseInt(voterId), candidateName: selectedCandidateName })
                    });
                    
                    const resultMessage = await response.text();
                    
                    if(response.ok) {
                        showMessage('vote-message', 'Vote cast successfully! Thank you for participating.', 'success');
                        document.querySelector('.vote-submission').style.display = 'none';
                        document.querySelectorAll('.candidate-card').forEach(card => card.style.pointerEvents = 'none');
                    } else {
                        showMessage('vote-message', resultMessage, 'error');
                    }
                } catch (error) {
                    console.error('Error casting vote:', error);
                    showMessage('vote-message', 'An error occurred. Please try again.', 'error');
                }
            }
        });

        loadCandidates();
    }

    // --- Results Page Logic ---
    if (document.body.id === 'results-page') {
        const resultsContainer = document.getElementById('results-container');
        const loadResults = async () => {
             try {
                const response = await fetch(`${API_BASE_URL}/votes/summary`);
                const results = await response.json();
                
                const totalVotes = results.reduce((sum, c) => sum + c.voteCount, 0);

                resultsContainer.innerHTML = '';
                results.sort((a,b) => b.voteCount - a.voteCount).forEach(c => {
                    const percentage = totalVotes > 0 ? ((c.voteCount / totalVotes) * 100).toFixed(1) : 0;
                    const resultEl = document.createElement('div');
                    resultEl.className = 'result-bar';
                    resultEl.innerHTML = `
                        <div class="result-info">
                            <span>${c.candidateName}</span>
                            <span>${c.voteCount} Votes (${percentage}%)</span>
                        </div>
                        <div class="bar-container">
                            <div class="bar-fill" style="width: 0%;"></div>
                        </div>
                    `;
                    resultsContainer.appendChild(resultEl);
                    // Animate the bar fill
                    setTimeout(() => {
                        resultEl.querySelector('.bar-fill').style.width = `${percentage}%`;
                    }, 100);
                });

             } catch(error) {
                 console.error("Error loading results:", error);
                 resultsContainer.innerHTML = '<p class="message error">Could not load election results.</p>';
             }
        };
        loadResults();
        setInterval(loadResults, 10000); // Auto-refresh every 10 seconds
    }
    
    // --- Admin Page Logic ---
    if (document.body.id === 'admin-page') {
        const loginView = document.getElementById('admin-login-view');
        const dashboardView = document.getElementById('admin-dashboard-view');
        const loginForm = document.getElementById('admin-login-form');
        let adminAuthHeader = null;

        const getAuthHeader = () => {
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;
            return 'Basic ' + btoa(username + ':' + password);
        }
        
        const fetchWithAuth = (url, options = {}) => {
            const headers = { ...options.headers, 'Authorization': adminAuthHeader };
            return fetch(url, { ...options, headers });
        }

        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            adminAuthHeader = getAuthHeader();
            
            try {
                // Test authentication by fetching results
                const response = await fetchWithAuth(`${API_BASE_URL}/admin/results`);
                if (response.ok) {
                    loginView.style.display = 'none';
                    dashboardView.style.display = 'block';
                    loadAdminData();
                } else {
                     showMessage('admin-login-message', 'Authentication failed. Check credentials.', 'error');
                }
            } catch(error) {
                showMessage('admin-login-message', 'Server error during login.', 'error');
            }
        });

        const loadAdminData = async () => {
            try {
                const response = await fetchWithAuth(`${API_BASE_URL}/admin/candidates`);
                const candidates = await response.json();
                const listEl = document.getElementById('manage-candidate-list');
                listEl.innerHTML = '';
                candidates.forEach(c => {
                    const item = document.createElement('div');
                    item.className = 'candidate-manage-item';
                    item.innerHTML = `
                        <span><strong>${c.name}</strong> (${c.party})</span>
                        <button class="btn btn-danger" data-id="${c.id}">Delete</button>
                    `;
                    listEl.appendChild(item);
                });
            } catch (error) {
                 console.error('Failed to load candidates for admin', error);
            }
        };
        
        document.getElementById('add-candidate-form').addEventListener('submit', async (e) => {
            e.preventDefault();
            const name = document.getElementById('candidate-name').value;
            const party = document.getElementById('candidate-party').value;
            
            try {
                const response = await fetchWithAuth(`${API_BASE_URL}/admin/candidates`, {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify({ name, party })
                });
                if(response.ok) {
                    showMessage('admin-action-message', `Candidate '${name}' added successfully.`, 'success');
                    e.target.reset();
                    loadAdminData();
                } else {
                     showMessage('admin-action-message', `Failed to add candidate.`, 'error');
                }
            } catch(error) {
                showMessage('admin-action-message', 'Server error.', 'error');
            }
        });

        document.getElementById('manage-candidate-list').addEventListener('click', async (e) => {
            if(e.target.matches('.btn-danger')) {
                const candidateId = e.target.dataset.id;
                if(confirm('Are you sure you want to delete this candidate?')) {
                     try {
                        const response = await fetchWithAuth(`${API_BASE_URL}/admin/candidates/${candidateId}`, { method: 'DELETE' });
                         if(response.ok) {
                             showMessage('admin-action-message', `Candidate deleted successfully.`, 'success');
                             loadAdminData();
                         } else {
                            showMessage('admin-action-message', 'Failed to delete candidate.', 'error');
                         }
                    } catch(error) {
                        showMessage('admin-action-message', 'Server error.', 'error');
                    }
                }
            }
        });
        
        document.getElementById('reset-election-btn').addEventListener('click', async () => {
            if(confirm('ARE YOU ABSOLUTELY SURE? This will delete all votes and reset the election.')) {
                 try {
                     const response = await fetchWithAuth(`${API_BASE_URL}/admin/reset`, { method: 'PUT' });
                      if(response.ok) {
                         showMessage('admin-action-message', `Election has been reset.`, 'success');
                         loadAdminData();
                     } else {
                        showMessage('admin-action-message', 'Failed to reset election.', 'error');
                     }
                } catch(error) {
                    showMessage('admin-action-message', 'Server error.', 'error');
                }
            }
        });
        // In app.js, add this new code block

// --- Voter Registration Page Logic ---
if (document.body.id === 'register-page') {
    const registerForm = document.getElementById('voter-registration-form');
    
    registerForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const name = document.getElementById('voterName').value;
        const email = document.getElementById('voterEmail').value;

        try {
            const response = await fetch(`${API_BASE_URL}/voters`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ name, email, hasVoted: false })
            });

            if (response.ok) {
                const newVoter = await response.json();
                showMessage('register-message', `Registration successful! Your new Voter ID is: ${newVoter.id}`, 'success');
                registerForm.reset();
            } else {
                const errorData = await response.text();
                showMessage('register-message', `Registration failed: ${errorData}`, 'error');
            }
        } catch (error) {
            console.error('Error during registration:', error);
            showMessage('register-message', 'Could not connect to the server. Please try again later.', 'error');
        }
    });
}
    }
});