import {
  DeleteOutlined,
  EditOutlined,
  PlusCircleOutlined,
} from "@ant-design/icons";
import { Button, message, Popconfirm, Table } from "antd";
import { useEffect, useState } from "react";
import CreateUserModal from "../components/modal/create-user.modal";
import { deleteUserAPI, getUsersAPI } from "../services/api";
import UpdateUserModal from "../components/modal/update-user.modal";

interface IUser {
  id: number;
  name: string;
  email: string;
}
const UserPage = () => {
  const [users, setUsers] = useState<IUser[]>([]);
  const [openCreateModal, setOpenCreateModal] = useState<boolean>(false);
  const [openUpdateModal, setOpenUpdateModal] = useState<boolean>(false);
  const [dataUpdate, setDataUpdate] = useState<IUser | null>(null);

  const getAllUsers = async () => {
    const response = await getUsersAPI();
    //console.log(">>> check response: ", response);
    if (response?.data?.status === "Success") {
      setUsers(response.data.data);
    }
  };
  useEffect(() => {
    const fetchUsers = async () => {
      const response = await getUsersAPI();
      if (response?.data?.status === "Success") {
        setUsers(response.data.data);
      }
    };

    fetchUsers();
  }, []);

  const handleClickUpdate = (data: IUser) => {
    setDataUpdate(data);
    setOpenUpdateModal(true);
  };

  const handleClickDelete = async (data: IUser) => {
    const response = await deleteUserAPI(data.id);
    console.log(">>> delete response: ", response);
    message.success("Xoá user thành công!");
    await getAllUsers();
  };

  const columns = [
    {
      title: "Id",
      dataIndex: "id",
    },
    {
      title: "Name",
      dataIndex: "name",
    },
    {
      title: "Email",
      dataIndex: "email",
    },
    {
      title: "Action",
      render: (_: string, record: IUser) => {
        return (
          <>
            <Button
              onClick={() => handleClickUpdate(record)}
              style={{
                cursor: "pointer",
                backgroundColor: "green",
                color: "white",
                marginRight: 10,
              }}
              icon={<EditOutlined />}
            >
              Update
            </Button>

            <Popconfirm
              title="Delete the user"
              description="Are you sure to delete this user?"
              onConfirm={() => handleClickDelete(record)}
              // onCancel={cancel}
              okText="Yes"
              cancelText="No"
            >
              <Button
                style={{
                  cursor: "pointer",
                  backgroundColor: "red",
                  color: "white",
                }}
                icon={<DeleteOutlined />}
              >
                Delete
              </Button>
            </Popconfirm>
          </>
        );
      },
    },
  ];

  return (
    <div style={{ padding: 10 }}>
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
        }}
      >
        <h3>Table Users</h3>
        <Button
          type="primary"
          icon={<PlusCircleOutlined />}
          onClick={() => setOpenCreateModal(true)}
        >
          Add a User
        </Button>
      </div>
      <Table bordered dataSource={users} columns={columns} rowKey={"id"} />
      <CreateUserModal
        openCreateModal={openCreateModal}
        setOpenCreateModal={setOpenCreateModal}
        getAllUsers={getAllUsers}
      />

      <UpdateUserModal
        openUpdateModal={openUpdateModal}
        setOpenUpdateModal={setOpenUpdateModal}
        getAllUsers={getAllUsers}
        dataUpdate={dataUpdate}
        setDataUpdate={setDataUpdate}
      />
    </div>
  );
};

export default UserPage;
