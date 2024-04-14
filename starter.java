import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class starter {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Search App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));

        JTextField searchField = new JTextField();
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);

        JComboBox<String> searchType = new JComboBox<>(new String[]{"Type", "Generation", "Attack Type"});
        SearchService searchService = new SearchService();

        JButton searchButton = new JButton("Search");
        JButton addPokemonButton = new JButton("Add Pokemon");
        JButton editPokemonButton = new JButton("Edit Pokemon");
        editPokemonButton.setForeground(Color.BLACK);
        addPokemonButton.setForeground(Color.BLACK);
        buttonPanel.add(addPokemonButton);
        buttonPanel.add(editPokemonButton);
        panel.add(buttonPanel, BorderLayout.EAST);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText();
                String selectedSearchType = (String) searchType.getSelectedItem();
                List<SearchService.Pokemon> results;
                if ("Type".equals(selectedSearchType)) {
                    results = searchService.searchByType(searchText);
                } else if ("Generation".equals(selectedSearchType)) {
                    results = searchService.searchByGeneration(Integer.parseInt(searchText));
                } else {
                    results = searchService.searchByAttackType(searchText);
                }
                resultArea.setText("Search results for: " + searchText + "\n");
                for (SearchService.Pokemon pokemon : results) {
                    resultArea.append(pokemon.getName() + " ");
                    JButton viewDetailsButton = new JButton("View Details");
                    viewDetailsButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JFrame detailsFrame = new JFrame("Pokemon Details");
                            detailsFrame.setSize(500, 400);

                            JPanel detailsPanel = new JPanel(new GridLayout(4, 1));

                            JTextArea abilitiesArea = new JTextArea("Abilities: " + pokemon.getAbilities());
                            abilitiesArea.setEditable(false);
                            JTextArea statsArea = new JTextArea("Stats: " + pokemon.getStats());
                            statsArea.setEditable(false);
                            JTextArea evolutionArea = new JTextArea("Evolution: " + pokemon.getEvolution());
                            evolutionArea.setEditable(false);

                            detailsPanel.add(abilitiesArea);
                            detailsPanel.add(statsArea);
                            detailsPanel.add(evolutionArea);

                            detailsFrame.getContentPane().add(detailsPanel);
                            detailsFrame.setVisible(true);
                        }
                    });
                    resultArea.append("\n");
                }
            }
        });

        panel.add(searchType, BorderLayout.WEST);
        panel.add(searchField, BorderLayout.NORTH);
        panel.add(resultArea, BorderLayout.CENTER);
        panel.add(searchButton, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        frame.setVisible(true);

        // JButton addPokemonButton = new JButton("Add Pokemon");
        addPokemonButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame addFrame = new JFrame("Add Pokemon");
            addFrame.setSize(500, 400);

            JPanel addPanel = new JPanel(new GridLayout(5, 2));

            JTextField nameField = new JTextField();
            JTextField typeField = new JTextField();
            JTextField abilitiesField = new JTextField();
            JTextField statsField = new JTextField();

            addPanel.add(new JLabel("Name:"));
            addPanel.add(nameField);
            addPanel.add(new JLabel("Type:"));
            addPanel.add(typeField);
            addPanel.add(new JLabel("Abilities:"));
            addPanel.add(abilitiesField);
            addPanel.add(new JLabel("Stats:"));
            addPanel.add(statsField);

            JButton submitButton = new JButton("Submit");
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = nameField.getText();
                    String type = typeField.getText();
                    String abilities = abilitiesField.getText();
                    String stats = statsField.getText();
                    searchService.addPokemon(name, type, abilities, stats);
                    addFrame.dispose();
                }
            });

            addPanel.add(submitButton);
            addFrame.getContentPane().add(addPanel);
            addFrame.setVisible(true);
        }
    });

    // buttonPanel.add(addPokemonButton, BorderLayout.EAST);

        // JButton editPokemonButton = new JButton("Edit Pokemon");
        editPokemonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame editFrame = new JFrame("Edit Pokemon");
                editFrame.setSize(500, 400);

                JPanel editPanel = new JPanel(new GridLayout(6, 2));

                JTextField nameField = new JTextField();
                JTextField typeField = new JTextField();
                JTextField abilitiesField = new JTextField();
                JTextField statsField = new JTextField();
                JTextField evolutionField = new JTextField();

                editPanel.add(new JLabel("Name:"));
                editPanel.add(nameField);
                editPanel.add(new JLabel("Type:"));
                editPanel.add(typeField);
                editPanel.add(new JLabel("Abilities:"));
                editPanel.add(abilitiesField);
                editPanel.add(new JLabel("Stats:"));
                editPanel.add(statsField);
                editPanel.add(new JLabel("Evolution:"));
                editPanel.add(evolutionField);

                JButton submitButton = new JButton("Submit");
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = nameField.getText();
                        String type = typeField.getText();
                        String abilities = abilitiesField.getText();
                        String stats = statsField.getText();
                        String evolution = evolutionField.getText();
                        searchService.editPokemon(name, type, abilities, stats, evolution);
                        editFrame.dispose();
                    }
                });

                editPanel.add(submitButton);
                editFrame.getContentPane().add(editPanel);
                editFrame.setVisible(true);
            }
        });

        // panel.add(editPokemonButton, BorderLayout.EAST);

    }

    static class SearchService {
        private static final String CONNECTION_STRING = "jdbc:sqlserver://[server-name];databaseName=[database-name];user=[username];password=[password]";

        void editPokemon(String name, String type, String abilities, String stats, String evolution) {
            String query = "UPDATE Pokemon SET Type = ?, Abilities = ?, Stats = ?, Evolution = ? WHERE Name = ?";
            executeUpdate(query, type, abilities, stats, evolution, name);
        }

        void addPokemon(String name, String type, String abilities, String stats) {
            String query = "INSERT INTO Pokemon (Name, Type, Abilities, Stats) VALUES (?, ?, ?, ?)";
            executeUpdate(query, name, type, abilities, stats);
        }
        
        private void executeUpdate(String query, String... params) {
            try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setString(i + 1, params[i]);
                }
                stmt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        class Pokemon {
            private String name;
            private String abilities;
            private String stats;
            private String evolution;
        
            public String getName() {
                return name;
            }
        
            public void setName(String name) {
                this.name = name;
            }
        
            public String getAbilities() {
                return abilities;
            }
        
            public void setAbilities(String abilities) {
                this.abilities = abilities;
            }
        
            public String getStats() {
                return stats;
            }
        
            public void setStats(String stats) {
                this.stats = stats;
            }
        
            public String getEvolution() {
                return evolution;
            }
        
            public void setEvolution(String evolution) {
                this.evolution = evolution;
            }
        }

        List<Pokemon> searchByType(String type) {
            return executeQuery("SELECT * FROM Pokemon WHERE PrimaryType = ? OR SecondaryType = ?", type, type);
        }

        List<Pokemon> searchByGeneration(int generation) {
            return executeQuery("SELECT * FROM Pokemon WHERE Generation = ?", String.valueOf(generation));
        }

        List<Pokemon> searchByAttackType(String attack){
            return executeQuery("Select * from Pokemon where AttackType = ?", attack);
        }

        private List<Pokemon> executeQuery(String query, String... params) {
            List<Pokemon> result = new ArrayList<>();
            try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
                PreparedStatement stmt = conn.prepareStatement(query)) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setString(i + 1, params[i]);
                }
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Pokemon pokemon = new Pokemon();
                    pokemon.setName(rs.getString("Name"));
                    pokemon.setAbilities(rs.getString("Abilities"));
                    pokemon.setStats(rs.getString("Stats"));
                    pokemon.setEvolution(rs.getString("Evolution"));
                    result.add(pokemon);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return result;
        }
    }
}