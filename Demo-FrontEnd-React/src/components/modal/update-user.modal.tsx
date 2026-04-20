import { App, Input, Modal } from "antd";
import axios from "axios";
import { useEffect, useState } from "react";
import { updateUserAPI } from "../../services/api";

interface IUserUpdate {
  id: number;
  name: string;
  email: string;
}

interface IProps {
  openUpdateModal: boolean;
  setOpenUpdateModal: (v: boolean) => void;
  getAllUsers: () => Promise<void>;
  dataUpdate: IUserUpdate | null;
  setDataUpdate: (v: IUserUpdate | null) => void;
}

const UpdateUserModal = (props: IProps) => {
  const { notification, message } = App.useApp();
  const {
    openUpdateModal,
    setOpenUpdateModal,
    getAllUsers,
    dataUpdate,
    setDataUpdate,
  } = props;
  const [name, setName] = useState<string>("");
  const [email, setEmail] = useState<string>("");
  const [loading, setLoading] = useState<boolean>(false);

  useEffect(() => {
    if (openUpdateModal && dataUpdate) {
      setName(dataUpdate.name);
      setEmail(dataUpdate.email);
    }
  }, [openUpdateModal, dataUpdate]);

  const handleSubmit = async () => {
    setLoading(true);
    if (dataUpdate) {
      try {
        const response = await updateUserAPI(dataUpdate.id, name, email);
        if (response.data.data) {
          message.success("Cập nhật user thành công!");
          setName("");
          setEmail("");
          setDataUpdate(null);
          setOpenUpdateModal(false);
          await getAllUsers();
        }
      } catch (error: unknown) {
        let messageError = "";
        if (axios.isAxiosError(error)) {
          messageError = error.response?.data?.message ?? "unknown!";
        }
        console.log(">>> check error: ", error);
        notification.error({
          message: "Có lỗi xảy ra!",
          description: messageError
            .split("\n")
            .map((item, index) => <div key={index}>{item}</div>),
        });
      } finally {
        setLoading(false);
      }
    }
  };

  return (
    <Modal
      title="Update a user"
      maskClosable={false}
      closable={{ "aria-label": "Custom Close Button" }}
      open={openUpdateModal}
      onOk={handleSubmit}
      onCancel={() => {
        setName("");
        setName("");
        setDataUpdate(null);
        setOpenUpdateModal(false);
      }}
      okText={"Update"}
      okButtonProps={{
        loading: loading,
      }}
    >
      <div
        style={{
          display: "flex",
          flexDirection: "column",
          gap: 10,
          marginBottom: 15,
        }}
      >
        <span>Name: </span>
        <Input value={name} onChange={(v) => setName(v.target.value)} />
      </div>
      <div style={{ display: "flex", flexDirection: "column", gap: 10 }}>
        <span>Email: </span>
        <Input value={email} onChange={(v) => setEmail(v.target.value)} />
      </div>
    </Modal>
  );
};

export default UpdateUserModal;
