import {
  Button,
  Dialog,
  DialogBackdrop,
  DialogPanel,
  DialogTitle,
} from "@headlessui/react";
import { Divider } from "@mui/material";
import { MdClose, MdDone } from "react-icons/md";
import { useState } from "react";
import Status from "./Status";

function ProductViewModal({ open, setOpen, product, isAvailable }) {
  const { productName, image, description, price, specialPrice } = product;

  return (
    <Dialog
      open={open}
      as="div"
      className="fixed inset-0 flex items-center justify-center z-50"
      onClose={() => setOpen(false)}
    >
      <DialogBackdrop className="fixed inset-0 bg-black bg-opacity-50 transition-opacity" />
      <div className="relative flex items-center justify-center w-full max-w-lg p-4">
        <DialogPanel className="relative bg-white rounded-xl shadow-2xl w-full overflow-hidden">
          <button
            onClick={() => setOpen(false)}
            className="absolute top-4 right-4 text-gray-500 hover:text-gray-800"
          >
            <MdClose size={24} />
          </button>

          {image && (
            <div className="w-full aspect-[3/2] bg-gray-100 flex justify-center items-center">
              <img
                src={image}
                alt={productName}
                className="object-cover w-full h-full rounded-t-xl"
              />
            </div>
          )}

          <div className="p-6">
            <DialogTitle className="text-2xl font-semibold text-gray-900 mb-3">
              {productName}
            </DialogTitle>
            <div className="flex justify-between items-center mb-4">
              <div className="text-lg font-semibold text-gray-800">
                {specialPrice ? (
                  <>
                    <span className="text-gray-400 line-through text-base">
                      ${Number(price).toFixed(2)}
                    </span>
                    <span className="ml-2 text-xl text-green-600">
                      ${Number(specialPrice).toFixed(2)}
                    </span>
                  </>
                ) : (
                  <span className="text-xl">${Number(price).toFixed(2)}</span>
                )}
              </div>
              {isAvailable ? (
                <Status
                  text="In Stock"
                  icon={MdDone}
                  bg="bg-teal-100"
                  color="text-teal-800"
                />
              ) : (
                <Status
                  text="Out-Of-Stock"
                  icon={MdClose}
                  bg="bg-red-100"
                  color="text-red-800"
                />
              )}
            </div>
            <Divider className="mb-4" />
            <p className="text-gray-600 leading-relaxed">{description}</p>
          </div>
          <div className="px-6 py-4 flex justify-end">
            <button
              onClick={() => setOpen(false)}
              className="px-4 py-2 text-sm font-medium text-white bg-gray-800 hover:bg-gray-900 rounded-lg transition"
            >
              Close
            </button>
          </div>
        </DialogPanel>
      </div>
    </Dialog>
  );
}

export default ProductViewModal;
