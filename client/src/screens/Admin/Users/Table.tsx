import React, {useState} from 'react';
import styled from 'styled-components';
import {User} from '../../../api/types';
import {Button, HTMLTable, InputGroup} from '@blueprintjs/core';
import {useHistory} from 'react-router';

const Container = styled.div`
    display: flex;
    flex-direction: row;
    align-self: flex-end;
    
    & > :not(:last-child) {
        margin-right: 1.5rem;
    }
`;

type Props = {
    users: User[]
    refetch: () => void | Promise<void>
}
const Table: React.FC<Props> = ({users, refetch}) => {
    const [filter, setFilter] = useState('');
    const [filteredUsers, setFilteredUsers] = useState(users);
    const history = useHistory();

    const filterUsers = (event: React.ChangeEvent<HTMLInputElement>) => {
        const filter = event.target.value;
        setFilter(filter);
        setFilteredUsers(users.filter(user => user.email.includes(filter)));
    };

    const goToUser = (id: string) => history.push(`/users/${id}`);

    return (
        <>
            <Container>
                <InputGroup leftIcon='search' placeholder='Search by email...' value={filter} onChange={filterUsers}/>
                <Button intent='primary' icon='refresh' onClick={refetch}>Refresh</Button>
            </Container>
            <HTMLTable striped>
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Role</th>
                    <th>Email</th>
                    <th>Last Login</th>
                    <th>Favorite Repo Count</th>
                    <th>Details</th>
                </tr>
                </thead>
                <tbody>
                {filteredUsers.map(user => (
                    <tr>
                        <td>{user.id}</td>
                        <td>{user.role}</td>
                        <td>{user.email}</td>
                        <td>{new Date(user.lastLogin).toISOString().replace('T', ' ').replace('Z', '')}</td>
                        <td>{user.favRepos.length}</td>
                        <td><Button intent='primary' icon='arrow-right' onClick={() => goToUser(user.id)}/></td>
                    </tr>
                ))}
                </tbody>
            </HTMLTable>
        </>
    );
};

export default Table;