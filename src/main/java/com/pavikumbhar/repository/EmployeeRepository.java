package com.pavikumbhar.repository;

import com.pavikumbhar.entity.Employee;
import com.pavikumbhar.model.EmployeeDTO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EmployeeRepository extends R2dbcRepository<Employee, Long> {
    @Query("""
    SELECT 
        e.id AS id,
        e.name AS name,
        e.email AS email,
        COALESCE(
            json_agg(
                json_build_object('id', a.id ,'city', a.city, 'state', a.state)
            ) FILTER (WHERE a.city IS NOT NULL OR a.state IS NOT NULL), 
            '[]'
        )::TEXT AS addresses_str
    FROM employee e
    LEFT JOIN address a ON e.id = a.employee_id
    WHERE e.active = true
    GROUP BY e.id, e.name, e.email order by e.id
    """)
    Flux<EmployeeDTO> findActiveEmployeesWithCities1();

    @Query("""
    SELECT 
        e.id AS id, 
        e.name AS name, 
        e.email AS email, 
        COALESCE((
            SELECT json_agg(
                json_build_object('id', a.id, 'city', a.city, 'state', a.state)
            ) 
            FROM address a
            WHERE a.employee_id = e.id AND (a.city IS NOT NULL OR a.state IS NOT NULL)
        ), '[]')::TEXT AS addresses_str 
    FROM employee e 
    WHERE e.active = true 
    ORDER BY e.id
""")
    Flux<EmployeeDTO> findActiveEmployeesWithCities2();

    @Query("""
    SELECT 
        e.id,
        e.name,
        e.email,
        COALESCE(addr.addresses, '[]')::TEXT AS addresses_str
    FROM employee e
    LEFT JOIN LATERAL (
        SELECT json_agg(json_build_object('id', a.id, 'city', a.city, 'state', a.state)) AS addresses
        FROM address a
        WHERE a.employee_id = e.id 
          AND (a.city IS NOT NULL OR a.state IS NOT NULL)
    ) addr ON true
    WHERE e.active = true
    ORDER BY e.id
""")
    Flux<EmployeeDTO> findActiveEmployeesWithCities();

}
