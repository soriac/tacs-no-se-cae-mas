import React from 'react';
import {ItemRenderer, Select} from '@blueprintjs/select';
import {User} from '../../../api/types';
import {Button, MenuItem} from '@blueprintjs/core';

const renderUser: ItemRenderer<User> = (user, {handleClick, modifiers}) =>
    <MenuItem
        key={user.id}
        active={modifiers.active}
        text={user.email}
        onClick={handleClick}/>;

type Props = {
    users: User[]
    selectedUser?: User
    setUser: (user: User) => void
}
const UserSelect: React.FC<Props> = ({users, selectedUser, setUser}) => {

    console.log(selectedUser);

    return (
        <Select
            popoverProps={{boundary: 'window'}}
            items={users}
            itemRenderer={renderUser}
            onItemSelect={setUser}>
            <Button>{selectedUser ? selectedUser.email : 'Select a user'}</Button>
        </Select>
    );

};

export default UserSelect;
