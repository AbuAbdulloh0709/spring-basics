package com.epam.esm.dto;

public class FilterRequest {
    private String name;
    private String description;
    private Sorting sorting;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Sorting getSorting() {
        return sorting;
    }

    public void setSorting(Sorting sorting) {
        this.sorting = sorting;
    }

    public String generateSqlQuery() {
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM gift_certificates gc ");

        if (name != null) {
            sqlBuilder.append("where name ILIKE '%").append(name).append("%'");
        }
        if (description != null) {
            if (name != null) {
                sqlBuilder.append(" and ");
            } else {
                sqlBuilder.append(" where ");
            }
            sqlBuilder.append("description ILIKE '%").append(description).append("%' ");
        }
        if (sorting != null && sorting.sortBy != null && sorting.sortOrder != null) {
            sqlBuilder.append(" order by ").append(sorting.sortBy.equals("date") ? "create_date" : sorting.sortBy).append(" ").append(sorting.sortOrder);
        }

        return sqlBuilder.toString();
    }


    public class Sorting {
        private String sortBy;
        private String sortOrder;

        public String getSortBy() {
            return sortBy;
        }

        public void setSortBy(String sortBy) {
            this.sortBy = sortBy;
        }

        public String getSortOrder() {
            return sortOrder;
        }

        public void setSortOrder(String sortOrder) {
            this.sortOrder = sortOrder;
        }


    }


    @Override
    public String toString() {
        return "FilterRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sorting=" + sorting +
                '}';
    }
}
